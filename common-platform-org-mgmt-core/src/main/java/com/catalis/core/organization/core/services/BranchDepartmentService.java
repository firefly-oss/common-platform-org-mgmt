package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.interfaces.dtos.BranchDepartmentDTO;

import reactor.core.publisher.Mono;

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
     * Creates a new branch department based on the provided information.
     *
     * @param branchDepartmentDTO the DTO object containing details of the branch department to be created
     * @return a Mono that emits the created BranchDepartmentDTO object
     */
    Mono<BranchDepartmentDTO> createBranchDepartment(BranchDepartmentDTO branchDepartmentDTO);
    
    /**
     * Updates an existing branch department with updated information.
     *
     * @param branchDepartmentId the unique identifier of the branch department to be updated
     * @param branchDepartmentDTO the data transfer object containing the updated details of the branch department
     * @return a reactive Mono containing the updated BranchDepartmentDTO
     */
    Mono<BranchDepartmentDTO> updateBranchDepartment(Long branchDepartmentId, BranchDepartmentDTO branchDepartmentDTO);
    
    /**
     * Deletes a branch department identified by its unique ID.
     *
     * @param branchDepartmentId the unique identifier of the branch department to be deleted
     * @return a Mono that completes when the branch department is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBranchDepartment(Long branchDepartmentId);
    
    /**
     * Retrieves a branch department by its unique identifier.
     *
     * @param branchDepartmentId the unique identifier of the branch department to retrieve
     * @return a Mono emitting the {@link BranchDepartmentDTO} representing the branch department if found,
     *         or an empty Mono if the branch department does not exist
     */
    Mono<BranchDepartmentDTO> getBranchDepartmentById(Long branchDepartmentId);
}