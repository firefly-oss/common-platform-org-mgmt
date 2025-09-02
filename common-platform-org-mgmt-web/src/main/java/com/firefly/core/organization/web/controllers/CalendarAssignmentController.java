package com.firefly.core.organization.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.services.CalendarAssignmentService;
import com.firefly.core.organization.interfaces.dtos.CalendarAssignmentDTO;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/banks/{bankId}/calendars/{calendarId}/assignments")
@Tag(name = "Calendar Assignment Management", description = "APIs for managing assignments of a specific working calendar")
public class CalendarAssignmentController {

    @Autowired
    private CalendarAssignmentService calendarAssignmentService;

    @Operation(summary = "Get all assignments for a calendar with filtering", description = "Returns a paginated list of assignments for a specific working calendar based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved calendar assignments",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "404", description = "Bank or calendar not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<CalendarAssignmentDTO>> filterCalendarAssignments(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the calendar", required = true)
            @PathVariable UUID calendarId,
            @Parameter(description = "Filter criteria for calendar assignments", required = true)
            @Valid @RequestBody FilterRequest<CalendarAssignmentDTO> filterRequest) {
        return calendarAssignmentService.filterCalendarAssignmentsForCalendar(bankId, calendarId, filterRequest);
    }

    @Operation(summary = "Create a new assignment for a calendar", description = "Creates a new assignment for a specific working calendar with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Calendar assignment successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarAssignmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid calendar assignment data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank or calendar not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CalendarAssignmentDTO> createCalendarAssignment(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the calendar", required = true)
            @PathVariable UUID calendarId,
            @Parameter(description = "Calendar assignment details to create", required = true)
            @Valid @RequestBody CalendarAssignmentDTO calendarAssignmentDTO) {
        return calendarAssignmentService.createCalendarAssignmentForCalendar(bankId, calendarId, calendarAssignmentDTO);
    }

    @Operation(summary = "Get calendar assignment by ID", description = "Returns an assignment of a specific working calendar based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved calendar assignment",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarAssignmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bank, calendar, or assignment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{assignmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CalendarAssignmentDTO> getCalendarAssignmentById(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the calendar", required = true)
            @PathVariable UUID calendarId,
            @Parameter(description = "ID of the assignment to retrieve", required = true)
            @PathVariable UUID assignmentId) {
        return calendarAssignmentService.getCalendarAssignmentByIdForCalendar(bankId, calendarId, assignmentId);
    }

    @Operation(summary = "Update calendar assignment", description = "Updates an existing assignment of a specific working calendar with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calendar assignment successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarAssignmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid calendar assignment data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank, calendar, or assignment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{assignmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CalendarAssignmentDTO> updateCalendarAssignment(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the calendar", required = true)
            @PathVariable UUID calendarId,
            @Parameter(description = "ID of the assignment to update", required = true)
            @PathVariable UUID assignmentId,
            @Parameter(description = "Updated calendar assignment details", required = true)
            @Valid @RequestBody CalendarAssignmentDTO calendarAssignmentDTO) {
        return calendarAssignmentService.updateCalendarAssignmentForCalendar(bankId, calendarId, assignmentId, calendarAssignmentDTO);
    }

    @Operation(summary = "Delete calendar assignment", description = "Deletes an assignment of a specific working calendar based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Calendar assignment successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Bank, calendar, or assignment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{assignmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCalendarAssignment(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the calendar", required = true)
            @PathVariable UUID calendarId,
            @Parameter(description = "ID of the assignment to delete", required = true)
            @PathVariable UUID assignmentId) {
        return calendarAssignmentService.deleteCalendarAssignmentForCalendar(bankId, calendarId, assignmentId);
    }
}
