package com.firefly.core.organization.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.services.BankRegionService;
import com.firefly.core.organization.interfaces.dtos.BankRegionDTO;
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
@RequestMapping("/api/v1/banks/{bankId}/divisions/{divisionId}/regions")
@Tag(name = "Bank Region Management", description = "APIs for managing bank regions")
public class BankRegionController {

    @Autowired
    private BankRegionService bankRegionService;

    @Operation(summary = "Get all regions for a bank division with filtering", description = "Returns a paginated list of regions for a specific bank division based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bank regions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "404", description = "Bank or division not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<BankRegionDTO>> filterBankRegions(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the division", required = true)
            @PathVariable UUID divisionId,
            @Parameter(description = "Filter criteria for bank regions", required = true)
            @Valid @RequestBody FilterRequest<BankRegionDTO> filterRequest) {
        return bankRegionService.filterBankRegionsForDivision(bankId, divisionId, filterRequest);
    }

    @Operation(summary = "Create a new region for a bank division", description = "Creates a new region for a specific bank division with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bank region successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankRegionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bank region data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank or division not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankRegionDTO> createBankRegion(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the division", required = true)
            @PathVariable UUID divisionId,
            @Parameter(description = "Bank region details to create", required = true)
            @Valid @RequestBody BankRegionDTO bankRegionDTO) {
        return bankRegionService.createBankRegionForDivision(bankId, divisionId, bankRegionDTO);
    }

    @Operation(summary = "Get bank region by ID", description = "Returns a region of a specific bank division based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bank region",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankRegionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bank, division, or region not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{regionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankRegionDTO> getBankRegionById(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the division", required = true)
            @PathVariable UUID divisionId,
            @Parameter(description = "ID of the region to retrieve", required = true)
            @PathVariable UUID regionId) {
        return bankRegionService.getBankRegionByIdForDivision(bankId, divisionId, regionId);
    }

    @Operation(summary = "Update bank region", description = "Updates an existing region of a specific bank division with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bank region successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankRegionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bank region data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank, division, or region not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{regionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankRegionDTO> updateBankRegion(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the division", required = true)
            @PathVariable UUID divisionId,
            @Parameter(description = "ID of the region to update", required = true)
            @PathVariable UUID regionId,
            @Parameter(description = "Updated bank region details", required = true)
            @Valid @RequestBody BankRegionDTO bankRegionDTO) {
        return bankRegionService.updateBankRegionForDivision(bankId, divisionId, regionId, bankRegionDTO);
    }

    @Operation(summary = "Delete bank region", description = "Deletes a region of a specific bank division based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bank region successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Bank, division, or region not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{regionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBankRegion(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the division", required = true)
            @PathVariable UUID divisionId,
            @Parameter(description = "ID of the region to delete", required = true)
            @PathVariable UUID regionId) {
        return bankRegionService.deleteBankRegionForDivision(bankId, divisionId, regionId);
    }
}
