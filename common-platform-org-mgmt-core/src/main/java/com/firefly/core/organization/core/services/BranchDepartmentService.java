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


package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.interfaces.dtos.BranchDepartmentDTO;

import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing branch departments.
 */
public interface BranchDepartmentService {
    /**
     * Filters the branch departments based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for BranchDepartmentDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of branch departments
     */
    Mono<PaginationResponse<BranchDepartmentDTO>> filterBranchDepartments(FilterRequest<BranchDepartmentDTO> filterRequest);

    /**
     * Filters the branch departments for a specific branch based on the given criteria.
     *
     * @param branchId the unique identifier of the branch
     * @param filterRequest the request object containing filtering criteria for BranchDepartmentDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of branch departments
     */
    Mono<PaginationResponse<BranchDepartmentDTO>> filterBranchDepartmentsForBranch(UUID branchId, FilterRequest<BranchDepartmentDTO> filterRequest);

    /**
     * Creates a new branch department based on the provided information.
     *
     * @param branchDepartmentDTO the DTO object containing details of the branch department to be created
     * @return a Mono that emits the created BranchDepartmentDTO object
     */
    Mono<BranchDepartmentDTO> createBranchDepartment(BranchDepartmentDTO branchDepartmentDTO);

    /**
     * Creates a new branch department for a specific branch based on the provided information.
     *
     * @param branchId the unique identifier of the branch
     * @param branchDepartmentDTO the DTO object containing details of the branch department to be created
     * @return a Mono that emits the created BranchDepartmentDTO object
     */
    Mono<BranchDepartmentDTO> createBranchDepartmentForBranch(UUID branchId, BranchDepartmentDTO branchDepartmentDTO);

    /**
     * Updates an existing branch department with updated information.
     *
     * @param branchDepartmentId the unique identifier of the branch department to be updated
     * @param branchDepartmentDTO the data transfer object containing the updated details of the branch department
     * @return a reactive Mono containing the updated BranchDepartmentDTO
     */
    Mono<BranchDepartmentDTO> updateBranchDepartment(UUID branchDepartmentId, BranchDepartmentDTO branchDepartmentDTO);

    /**
     * Updates an existing branch department for a specific branch with updated information.
     *
     * @param branchId the unique identifier of the branch
     * @param departmentId the unique identifier of the department to be updated
     * @param branchDepartmentDTO the data transfer object containing the updated details of the branch department
     * @return a reactive Mono containing the updated BranchDepartmentDTO
     */
    Mono<BranchDepartmentDTO> updateBranchDepartmentForBranch(UUID branchId, UUID departmentId, BranchDepartmentDTO branchDepartmentDTO);

    /**
     * Deletes a branch department identified by its unique ID.
     *
     * @param branchDepartmentId the unique identifier of the branch department to be deleted
     * @return a Mono that completes when the branch department is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBranchDepartment(UUID branchDepartmentId);

    /**
     * Deletes a branch department for a specific branch identified by its unique ID.
     *
     * @param branchId the unique identifier of the branch
     * @param departmentId the unique identifier of the department to be deleted
     * @return a Mono that completes when the branch department is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBranchDepartmentForBranch(UUID branchId, UUID departmentId);

    /**
     * Retrieves a branch department by its unique identifier.
     *
     * @param branchDepartmentId the unique identifier of the branch department to retrieve
     * @return a Mono emitting the {@link BranchDepartmentDTO} representing the branch department if found,
     *         or an empty Mono if the branch department does not exist
     */
    Mono<BranchDepartmentDTO> getBranchDepartmentById(UUID branchDepartmentId);

    /**
     * Retrieves a branch department for a specific branch by its unique identifier.
     *
     * @param branchId the unique identifier of the branch
     * @param departmentId the unique identifier of the department to retrieve
     * @return a Mono emitting the {@link BranchDepartmentDTO} representing the department if found,
     *         or an empty Mono if the department does not exist or doesn't belong to the specified branch
     */
    Mono<BranchDepartmentDTO> getBranchDepartmentByIdForBranch(UUID branchId, UUID departmentId);
}
