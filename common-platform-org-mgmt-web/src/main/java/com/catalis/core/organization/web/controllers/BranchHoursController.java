package com.catalis.core.organization.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.services.BranchHoursService;
import com.catalis.core.organization.core.services.BranchService;
import com.catalis.core.organization.interfaces.dtos.BranchHoursDTO;
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
@RequestMapping("/api/v1/branches/{branchId}/hours")
@Tag(name = "Branch Hours Management", description = "APIs for managing operating hours of a specific branch")
public class BranchHoursController {

    @Autowired
    private BranchHoursService branchHoursService;

    @Autowired
    private BranchService branchService;

    @Operation(summary = "Get all hours for a branch with filtering", description = "Returns a paginated list of operating hours for a specific branch based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved branch hours",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "404", description = "Branch not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<BranchHoursDTO>> filterBranchHours(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable Long branchId,
            @Parameter(description = "Filter criteria for branch hours", required = true)
            @Valid @RequestBody FilterRequest<BranchHoursDTO> filterRequest) {
        // Verify that the branch exists
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> {
                    // In a real implementation, you might want to add branchId to the filter criteria
                    // For now, we'll just pass the filter request to the service
                    return branchHoursService.filterBranchHours(filterRequest);
                });
    }

    @Operation(summary = "Create new operating hours for a branch", description = "Creates new operating hours for a specific branch with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Branch hours successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchHoursDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid branch hours data supplied"),
            @ApiResponse(responseCode = "404", description = "Branch not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchHoursDTO> createBranchHours(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable Long branchId,
            @Parameter(description = "Branch hours details to create", required = true)
            @Valid @RequestBody BranchHoursDTO branchHoursDTO) {
        // Verify that the branch exists
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> {
                    // Set the branchId from the path variable
                    branchHoursDTO.setBranchId(branchId);
                    return branchHoursService.createBranchHours(branchHoursDTO);
                });
    }

    @Operation(summary = "Get branch hours by ID", description = "Returns operating hours of a specific branch based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved branch hours",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchHoursDTO.class))),
            @ApiResponse(responseCode = "404", description = "Branch or hours not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{hoursId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BranchHoursDTO> getBranchHoursById(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable Long branchId,
            @Parameter(description = "ID of the hours to retrieve", required = true)
            @PathVariable Long hoursId) {
        // Verify that the branch exists
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> branchHoursService.getBranchHoursById(hoursId))
                .filter(hours -> hours.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Hours not found for branch with ID: " + branchId)));
    }

    @Operation(summary = "Update branch hours", description = "Updates existing operating hours of a specific branch with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch hours successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchHoursDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid branch hours data supplied"),
            @ApiResponse(responseCode = "404", description = "Branch or hours not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{hoursId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BranchHoursDTO> updateBranchHours(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable Long branchId,
            @Parameter(description = "ID of the hours to update", required = true)
            @PathVariable Long hoursId,
            @Parameter(description = "Updated branch hours details", required = true)
            @Valid @RequestBody BranchHoursDTO branchHoursDTO) {
        // Verify that the branch exists
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> branchHoursService.getBranchHoursById(hoursId))
                .filter(hours -> hours.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Hours not found for branch with ID: " + branchId)))
                .flatMap(hours -> {
                    // Set the branchId from the path variable
                    branchHoursDTO.setBranchId(branchId);
                    return branchHoursService.updateBranchHours(hoursId, branchHoursDTO);
                });
    }

    @Operation(summary = "Delete branch hours", description = "Deletes operating hours of a specific branch based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Branch hours successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Branch or hours not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{hoursId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBranchHours(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable Long branchId,
            @Parameter(description = "ID of the hours to delete", required = true)
            @PathVariable Long hoursId) {
        // Verify that the branch exists
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> branchHoursService.getBranchHoursById(hoursId))
                .filter(hours -> hours.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Hours not found for branch with ID: " + branchId)))
                .flatMap(hours -> branchHoursService.deleteBranchHours(hoursId));
    }
}