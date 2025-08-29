package com.firefly.core.organization.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.services.BankService;
import com.firefly.core.organization.interfaces.dtos.BankDTO;
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
@RequestMapping("/api/v1/banks")
@Tag(name = "Bank Management", description = "APIs for managing banks")
public class BankController {

    @Autowired
    private BankService bankService;

    @Operation(summary = "Get all banks with filtering", description = "Returns a paginated list of banks based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved banks",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<BankDTO>> filterBanks(
            @Parameter(description = "Filter criteria for banks", required = true)
            @Valid @RequestBody FilterRequest<BankDTO> filterRequest) {
        return bankService.filterBanks(filterRequest);
    }

    @Operation(summary = "Create a new bank", description = "Creates a new bank with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bank successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bank data supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankDTO> createBank(
            @Parameter(description = "Bank details to create", required = true)
            @Valid @RequestBody BankDTO bankDTO) {
        return bankService.createBank(bankDTO);
    }

    @Operation(summary = "Get bank by ID", description = "Returns a bank based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bank",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bank not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{bankId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankDTO> getBankById(
            @Parameter(description = "ID of the bank to retrieve", required = true)
            @PathVariable Long bankId) {
        return bankService.getBankById(bankId);
    }

    @Operation(summary = "Update bank", description = "Updates an existing bank with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bank successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bank data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{bankId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankDTO> updateBank(
            @Parameter(description = "ID of the bank to update", required = true)
            @PathVariable Long bankId,
            @Parameter(description = "Updated bank details", required = true)
            @Valid @RequestBody BankDTO bankDTO) {
        return bankService.updateBank(bankId, bankDTO);
    }

    @Operation(summary = "Delete bank", description = "Deletes a bank based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bank successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Bank not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{bankId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBank(
            @Parameter(description = "ID of the bank to delete", required = true)
            @PathVariable Long bankId) {
        return bankService.deleteBank(bankId);
    }
}