package com.firefly.core.organization.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.services.BankDivisionService;
import com.firefly.core.organization.interfaces.dtos.BankDivisionDTO;
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
@RequestMapping("/api/v1/banks/{bankId}/divisions")
@Tag(name = "Bank Division Management", description = "APIs for managing bank divisions")
public class BankDivisionController {

    @Autowired
    private BankDivisionService bankDivisionService;

    @Operation(summary = "Get all divisions for a bank with filtering", description = "Returns a paginated list of divisions for a specific bank based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bank divisions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "404", description = "Bank not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<BankDivisionDTO>> filterBankDivisions(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "Filter criteria for bank divisions", required = true)
            @Valid @RequestBody FilterRequest<BankDivisionDTO> filterRequest) {
        if(null != filterRequest.getFilters()){
            filterRequest.getFilters().setBankId(bankId);
        }
        return bankDivisionService.filterBankDivisions(filterRequest);
    }

    @Operation(summary = "Create a new division for a bank", description = "Creates a new division for a specific bank with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bank division successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankDivisionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bank division data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankDivisionDTO> createBankDivision(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "Bank division details to create", required = true)
            @Valid @RequestBody BankDivisionDTO bankDivisionDTO) {
        // Set the bankId from the path variable
        bankDivisionDTO.setBankId(bankId);
        return bankDivisionService.createBankDivision(bankDivisionDTO);
    }

    @Operation(summary = "Get bank division by ID", description = "Returns a division of a specific bank based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bank division",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankDivisionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bank or division not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{divisionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankDivisionDTO> getBankDivisionById(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the division to retrieve", required = true)
            @PathVariable UUID divisionId) {
        return bankDivisionService.getBankDivisionByIdForBank(bankId, divisionId);
    }

    @Operation(summary = "Update bank division", description = "Updates an existing division of a specific bank with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bank division successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankDivisionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bank division data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank or division not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{divisionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankDivisionDTO> updateBankDivision(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the division to update", required = true)
            @PathVariable UUID divisionId,
            @Parameter(description = "Updated bank division details", required = true)
            @Valid @RequestBody BankDivisionDTO bankDivisionDTO) {
        // Set the bankId from the path variable
        bankDivisionDTO.setBankId(bankId);
        return bankDivisionService.updateBankDivisionForBank(bankId, divisionId, bankDivisionDTO);
    }

    @Operation(summary = "Delete bank division", description = "Deletes a division of a specific bank based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bank division successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Bank or division not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{divisionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBankDivision(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the division to delete", required = true)
            @PathVariable UUID divisionId) {
        return bankDivisionService.deleteBankDivisionForBank(bankId, divisionId);
    }
}
