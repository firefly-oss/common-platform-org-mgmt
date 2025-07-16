package com.catalis.core.organization.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.services.BranchPositionService;
import com.catalis.core.organization.interfaces.dtos.BranchPositionDTO;
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
@RequestMapping("/api/v1/branches/{branchId}/departments/{departmentId}/positions")
@Tag(name = "Branch Position Management", description = "APIs for managing positions of a specific branch department")
public class BranchPositionController {

    @Autowired
    private BranchPositionService branchPositionService;

    @Operation(summary = "Get all positions for a branch department with filtering", description = "Returns a paginated list of positions for a specific branch department based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved branch positions",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "404", description = "Branch or department not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<BranchPositionDTO>> filterBranchPositions(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable Long branchId,
            @Parameter(description = "ID of the department", required = true)
            @PathVariable Long departmentId,
            @Parameter(description = "Filter criteria for branch positions", required = true)
            @Valid @RequestBody FilterRequest<BranchPositionDTO> filterRequest) {
        return branchPositionService.filterBranchPositionsForDepartment(branchId, departmentId, filterRequest);
    }

    @Operation(summary = "Create a new position for a branch department", description = "Creates a new position for a specific branch department with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Branch position successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchPositionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid branch position data supplied"),
            @ApiResponse(responseCode = "404", description = "Branch or department not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchPositionDTO> createBranchPosition(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable Long branchId,
            @Parameter(description = "ID of the department", required = true)
            @PathVariable Long departmentId,
            @Parameter(description = "Branch position details to create", required = true)
            @Valid @RequestBody BranchPositionDTO branchPositionDTO) {
        return branchPositionService.createBranchPositionForDepartment(branchId, departmentId, branchPositionDTO);
    }

    @Operation(summary = "Get branch position by ID", description = "Returns a position of a specific branch department based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved branch position",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchPositionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Branch, department, or position not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{positionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BranchPositionDTO> getBranchPositionById(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable Long branchId,
            @Parameter(description = "ID of the department", required = true)
            @PathVariable Long departmentId,
            @Parameter(description = "ID of the position to retrieve", required = true)
            @PathVariable Long positionId) {
        return branchPositionService.getBranchPositionByIdForDepartment(branchId, departmentId, positionId);
    }

    @Operation(summary = "Update branch position", description = "Updates an existing position of a specific branch department with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch position successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchPositionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid branch position data supplied"),
            @ApiResponse(responseCode = "404", description = "Branch, department, or position not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{positionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BranchPositionDTO> updateBranchPosition(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable Long branchId,
            @Parameter(description = "ID of the department", required = true)
            @PathVariable Long departmentId,
            @Parameter(description = "ID of the position to update", required = true)
            @PathVariable Long positionId,
            @Parameter(description = "Updated branch position details", required = true)
            @Valid @RequestBody BranchPositionDTO branchPositionDTO) {
        return branchPositionService.updateBranchPositionForDepartment(branchId, departmentId, positionId, branchPositionDTO);
    }

    @Operation(summary = "Delete branch position", description = "Deletes a position of a specific branch department based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Branch position successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Branch, department, or position not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{positionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBranchPosition(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable Long branchId,
            @Parameter(description = "ID of the department", required = true)
            @PathVariable Long departmentId,
            @Parameter(description = "ID of the position to delete", required = true)
            @PathVariable Long positionId) {
        return branchPositionService.deleteBranchPositionForDepartment(branchId, departmentId, positionId);
    }
}
