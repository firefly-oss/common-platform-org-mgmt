package com.firefly.core.organization.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.services.BranchService;
import com.firefly.core.organization.interfaces.dtos.BranchDTO;
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
@RequestMapping("/api/v1/banks/{bankId}/branches")
@Tag(name = "Bank Branch Management", description = "APIs for managing branches of a specific bank")
public class BankBranchController {

    @Autowired
    private BranchService branchService;

    @Operation(summary = "Get all branches for a bank with filtering", description = "Returns a paginated list of branches for a specific bank based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved branches",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "404", description = "Bank not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<BranchDTO>> filterBranchesForBank(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "Filter criteria for branches", required = true)
            @Valid @RequestBody FilterRequest<BranchDTO> filterRequest) {
        return branchService.filterBranchesForBank(bankId, filterRequest);
    }

    @Operation(summary = "Create a new branch for a bank", description = "Creates a new branch for a specific bank with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Branch successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid branch data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchDTO> createBranchForBank(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "Branch details to create", required = true)
            @Valid @RequestBody BranchDTO branchDTO) {
        return branchService.createBranchForBank(bankId, branchDTO);
    }

    @Operation(summary = "Get branch by ID for a bank", description = "Returns a branch of a specific bank based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved branch",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bank or branch not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BranchDTO> getBranchByIdForBank(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the branch to retrieve", required = true)
            @PathVariable UUID branchId) {
        return branchService.getBranchByIdForBank(bankId, branchId);
    }

    @Operation(summary = "Update branch for a bank", description = "Updates an existing branch of a specific bank with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid branch data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank or branch not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BranchDTO> updateBranchForBank(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the branch to update", required = true)
            @PathVariable UUID branchId,
            @Parameter(description = "Updated branch details", required = true)
            @Valid @RequestBody BranchDTO branchDTO) {
        return branchService.updateBranchForBank(bankId, branchId, branchDTO);
    }

    @Operation(summary = "Delete branch for a bank", description = "Deletes a branch of a specific bank based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Branch successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Bank or branch not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{branchId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBranchForBank(
            @Parameter(description = "ID of the bank", required = true)
            @PathVariable UUID bankId,
            @Parameter(description = "ID of the branch to delete", required = true)
            @PathVariable UUID branchId) {
        return branchService.deleteBranchForBank(bankId, branchId);
    }
}
