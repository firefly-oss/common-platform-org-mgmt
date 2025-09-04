/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.organization.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.services.BranchDepartmentService;
import com.firefly.core.organization.interfaces.dtos.BranchDepartmentDTO;
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
@RequestMapping("/api/v1/branches/{branchId}/departments")
@Tag(name = "Branch Department Management", description = "APIs for managing departments of a specific branch")
public class BranchDepartmentController {

    @Autowired
    private BranchDepartmentService branchDepartmentService;

    @Operation(summary = "Get all departments for a branch with filtering", description = "Returns a paginated list of departments for a specific branch based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved branch departments",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "404", description = "Branch not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<BranchDepartmentDTO>> filterBranchDepartments(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable UUID branchId,
            @Parameter(description = "Filter criteria for branch departments", required = true)
            @Valid @RequestBody FilterRequest<BranchDepartmentDTO> filterRequest) {
        return branchDepartmentService.filterBranchDepartmentsForBranch(branchId, filterRequest);
    }

    @Operation(summary = "Create a new department for a branch", description = "Creates a new department for a specific branch with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Branch department successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDepartmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid branch department data supplied"),
            @ApiResponse(responseCode = "404", description = "Branch not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchDepartmentDTO> createBranchDepartment(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable UUID branchId,
            @Parameter(description = "Branch department details to create", required = true)
            @Valid @RequestBody BranchDepartmentDTO branchDepartmentDTO) {
        return branchDepartmentService.createBranchDepartmentForBranch(branchId, branchDepartmentDTO);
    }

    @Operation(summary = "Get branch department by ID", description = "Returns a department of a specific branch based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved branch department",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDepartmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Branch or department not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{departmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BranchDepartmentDTO> getBranchDepartmentById(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable UUID branchId,
            @Parameter(description = "ID of the department to retrieve", required = true)
            @PathVariable UUID departmentId) {
        return branchDepartmentService.getBranchDepartmentByIdForBranch(branchId, departmentId);
    }

    @Operation(summary = "Update branch department", description = "Updates an existing department of a specific branch with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch department successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDepartmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid branch department data supplied"),
            @ApiResponse(responseCode = "404", description = "Branch or department not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{departmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BranchDepartmentDTO> updateBranchDepartment(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable UUID branchId,
            @Parameter(description = "ID of the department to update", required = true)
            @PathVariable UUID departmentId,
            @Parameter(description = "Updated branch department details", required = true)
            @Valid @RequestBody BranchDepartmentDTO branchDepartmentDTO) {
        return branchDepartmentService.updateBranchDepartmentForBranch(branchId, departmentId, branchDepartmentDTO);
    }

    @Operation(summary = "Delete branch department", description = "Deletes a department of a specific branch based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Branch department successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Branch or department not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBranchDepartment(
            @Parameter(description = "ID of the branch", required = true)
            @PathVariable UUID branchId,
            @Parameter(description = "ID of the department to delete", required = true)
            @PathVariable UUID departmentId) {
        return branchDepartmentService.deleteBranchDepartmentForBranch(branchId, departmentId);
    }
}
