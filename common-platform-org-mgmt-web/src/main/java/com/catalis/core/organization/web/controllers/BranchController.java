package com.catalis.core.organization.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.services.BranchService;
import com.catalis.core.organization.interfaces.dtos.BranchDTO;
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
@RequestMapping("/api/v1/branches")
@Tag(name = "Branch Management", description = "APIs for managing branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Operation(summary = "Get all branches with filtering", description = "Returns a paginated list of branches based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved branches",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<BranchDTO>> filterBranches(
            @Parameter(description = "Filter criteria for branches", required = true)
            @Valid @RequestBody FilterRequest<BranchDTO> filterRequest) {
        return branchService.filterBranches(filterRequest);
    }

    @Operation(summary = "Create a new branch", description = "Creates a new branch with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Branch successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid branch data supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchDTO> createBranch(
            @Parameter(description = "Branch details to create", required = true)
            @Valid @RequestBody BranchDTO branchDTO) {
        return branchService.createBranch(branchDTO);
    }

    @Operation(summary = "Get branch by ID", description = "Returns a branch based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved branch",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class))),
            @ApiResponse(responseCode = "404", description = "Branch not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BranchDTO> getBranchById(
            @Parameter(description = "ID of the branch to retrieve", required = true)
            @PathVariable Long branchId) {
        return branchService.getBranchById(branchId);
    }

    @Operation(summary = "Update branch", description = "Updates an existing branch with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid branch data supplied"),
            @ApiResponse(responseCode = "404", description = "Branch not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BranchDTO> updateBranch(
            @Parameter(description = "ID of the branch to update", required = true)
            @PathVariable Long branchId,
            @Parameter(description = "Updated branch details", required = true)
            @Valid @RequestBody BranchDTO branchDTO) {
        return branchService.updateBranch(branchId, branchDTO);
    }

    @Operation(summary = "Delete branch", description = "Deletes a branch based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Branch successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Branch not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{branchId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBranch(
            @Parameter(description = "ID of the branch to delete", required = true)
            @PathVariable Long branchId) {
        return branchService.deleteBranch(branchId);
    }
}