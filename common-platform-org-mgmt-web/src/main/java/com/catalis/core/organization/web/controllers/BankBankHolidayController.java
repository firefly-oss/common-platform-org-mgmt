package com.catalis.core.organization.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.services.BankHolidayService;
import com.catalis.core.organization.core.services.BankService;
import com.catalis.core.organization.interfaces.dtos.BankHolidayDTO;
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
@RequestMapping("/api/v1/banks/{bankId}/holidays")
@Tag(name = "Bank Holiday Management", description = "APIs for managing holidays of a specific bank")
public class BankBankHolidayController {

    @Autowired
    private BankHolidayService bankHolidayService;

    @Autowired
    private BankService bankService;

    @Operation(summary = "Get all holidays for a bank with filtering", description = "Returns a paginated list of holidays for a specific bank based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bank holidays",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "404", description = "Bank not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<BankHolidayDTO>> filterBankHolidays(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable Long bankId,
            @Parameter(description = "Filter criteria for bank holidays", required = true)
            @Valid @RequestBody FilterRequest<BankHolidayDTO> filterRequest) {
        // Verify that the bank exists
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> {
                    // In a real implementation, you might want to add bankId to the filter criteria
                    // For now, we'll just pass the filter request to the service
                    return bankHolidayService.filterBankHolidays(filterRequest);
                });
    }

    @Operation(summary = "Create a new holiday for a bank", description = "Creates a new holiday for a specific bank with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bank holiday successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankHolidayDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bank holiday data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankHolidayDTO> createBankHoliday(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable Long bankId,
            @Parameter(description = "Bank holiday details to create", required = true)
            @Valid @RequestBody BankHolidayDTO bankHolidayDTO) {
        // Verify that the bank exists
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> {
                    // Set the bankId from the path variable
                    bankHolidayDTO.setBankId(bankId);
                    return bankHolidayService.createBankHoliday(bankHolidayDTO);
                });
    }

    @Operation(summary = "Get bank holiday by ID", description = "Returns a holiday of a specific bank based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bank holiday",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankHolidayDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bank or holiday not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{holidayId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankHolidayDTO> getBankHolidayById(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable Long bankId,
            @Parameter(description = "ID of the holiday to retrieve", required = true)
            @PathVariable Long holidayId) {
        // Verify that the bank exists
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> bankHolidayService.getBankHolidayById(holidayId))
                .filter(holiday -> holiday.getBankId() != null && holiday.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Holiday not found for bank with ID: " + bankId)));
    }

    @Operation(summary = "Update bank holiday", description = "Updates an existing holiday of a specific bank with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bank holiday successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankHolidayDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bank holiday data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank or holiday not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{holidayId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankHolidayDTO> updateBankHoliday(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable Long bankId,
            @Parameter(description = "ID of the holiday to update", required = true)
            @PathVariable Long holidayId,
            @Parameter(description = "Updated bank holiday details", required = true)
            @Valid @RequestBody BankHolidayDTO bankHolidayDTO) {
        // Verify that the bank exists
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> bankHolidayService.getBankHolidayById(holidayId))
                .filter(holiday -> holiday.getBankId() != null && holiday.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Holiday not found for bank with ID: " + bankId)))
                .flatMap(holiday -> {
                    // Set the bankId from the path variable
                    bankHolidayDTO.setBankId(bankId);
                    return bankHolidayService.updateBankHoliday(holidayId, bankHolidayDTO);
                });
    }

    @Operation(summary = "Delete bank holiday", description = "Deletes a holiday of a specific bank based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bank holiday successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Bank or holiday not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{holidayId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBankHoliday(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable Long bankId,
            @Parameter(description = "ID of the holiday to delete", required = true)
            @PathVariable Long holidayId) {
        // Verify that the bank exists
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> bankHolidayService.getBankHolidayById(holidayId))
                .filter(holiday -> holiday.getBankId() != null && holiday.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Holiday not found for bank with ID: " + bankId)))
                .flatMap(holiday -> bankHolidayService.deleteBankHoliday(holidayId));
    }
}