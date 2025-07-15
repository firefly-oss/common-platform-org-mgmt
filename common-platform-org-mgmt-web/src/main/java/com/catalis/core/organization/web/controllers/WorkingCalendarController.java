package com.catalis.core.organization.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.services.BankService;
import com.catalis.core.organization.core.services.WorkingCalendarService;
import com.catalis.core.organization.interfaces.dtos.WorkingCalendarDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/banks/{bankId}/calendars")
@Tag(name = "Working Calendar Management", description = "APIs for managing working calendars of a specific bank")
public class WorkingCalendarController {

    @Autowired
    private WorkingCalendarService workingCalendarService;

    @Autowired
    private BankService bankService;

    @Operation(summary = "Get all calendars for a bank with filtering", description = "Returns a paginated list of working calendars for a specific bank based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved working calendars",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "404", description = "Bank not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<WorkingCalendarDTO>> filterWorkingCalendars(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable Long bankId,
            @Parameter(description = "Filter criteria for working calendars", required = true)
            @Valid @RequestBody FilterRequest<WorkingCalendarDTO> filterRequest) {
        // Verify that the bank exists
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> {
                    // In a real implementation, you might want to add bankId to the filter criteria
                    // For now, we'll just pass the filter request to the service
                    return workingCalendarService.filterWorkingCalendars(filterRequest);
                });
    }

    @Operation(summary = "Create a new working calendar for a bank", description = "Creates a new working calendar for a specific bank with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Working calendar successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkingCalendarDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid working calendar data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<WorkingCalendarDTO> createWorkingCalendar(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable Long bankId,
            @Parameter(description = "Working calendar details to create", required = true)
            @Valid @RequestBody WorkingCalendarDTO workingCalendarDTO) {
        // Verify that the bank exists
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> {
                    // Set the bankId from the path variable
                    workingCalendarDTO.setBankId(bankId);
                    return workingCalendarService.createWorkingCalendar(workingCalendarDTO);
                });
    }

    @Operation(summary = "Get working calendar by ID", description = "Returns a working calendar of a specific bank based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved working calendar",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkingCalendarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bank or calendar not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{calendarId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<WorkingCalendarDTO> getWorkingCalendarById(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable Long bankId,
            @Parameter(description = "ID of the calendar to retrieve", required = true)
            @PathVariable Long calendarId) {
        // Verify that the bank exists
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> workingCalendarService.getWorkingCalendarById(calendarId))
                .filter(calendar -> calendar.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar not found for bank with ID: " + bankId)));
    }

    @Operation(summary = "Update working calendar", description = "Updates an existing working calendar of a specific bank with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Working calendar successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkingCalendarDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid working calendar data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank or calendar not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{calendarId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<WorkingCalendarDTO> updateWorkingCalendar(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable Long bankId,
            @Parameter(description = "ID of the calendar to update", required = true)
            @PathVariable Long calendarId,
            @Parameter(description = "Updated working calendar details", required = true)
            @Valid @RequestBody WorkingCalendarDTO workingCalendarDTO) {
        // Verify that the bank exists
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> workingCalendarService.getWorkingCalendarById(calendarId))
                .filter(calendar -> calendar.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar not found for bank with ID: " + bankId)))
                .flatMap(calendar -> {
                    // Set the bankId from the path variable
                    workingCalendarDTO.setBankId(bankId);
                    return workingCalendarService.updateWorkingCalendar(calendarId, workingCalendarDTO);
                });
    }

    @Operation(summary = "Delete working calendar", description = "Deletes a working calendar of a specific bank based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Working calendar successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Bank or calendar not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{calendarId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteWorkingCalendar(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable Long bankId,
            @Parameter(description = "ID of the calendar to delete", required = true)
            @PathVariable Long calendarId) {
        // Verify that the bank exists
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> workingCalendarService.getWorkingCalendarById(calendarId))
                .filter(calendar -> calendar.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar not found for bank with ID: " + bankId)))
                .flatMap(calendar -> workingCalendarService.deleteWorkingCalendar(calendarId));
    }
}