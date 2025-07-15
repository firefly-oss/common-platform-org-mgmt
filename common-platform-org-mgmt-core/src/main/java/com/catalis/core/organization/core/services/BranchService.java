package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.interfaces.dtos.BranchDTO;

import reactor.core.publisher.Mono;

/**
 * Service interface for managing branches.
 */
public interface BranchService {
    /**
     * Filters the branches based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for BranchDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of branches
     */
    Mono<PaginationResponse<BranchDTO>> filterBranches(FilterRequest<BranchDTO> filterRequest);
    
    /**
     * Creates a new branch based on the provided information.
     *
     * @param branchDTO the DTO object containing details of the branch to be created
     * @return a Mono that emits the created BranchDTO object
     */
    Mono<BranchDTO> createBranch(BranchDTO branchDTO);
    
    /**
     * Updates an existing branch with updated information.
     *
     * @param branchId the unique identifier of the branch to be updated
     * @param branchDTO the data transfer object containing the updated details of the branch
     * @return a reactive Mono containing the updated BranchDTO
     */
    Mono<BranchDTO> updateBranch(Long branchId, BranchDTO branchDTO);
    
    /**
     * Deletes a branch identified by its unique ID.
     *
     * @param branchId the unique identifier of the branch to be deleted
     * @return a Mono that completes when the branch is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBranch(Long branchId);
    
    /**
     * Retrieves a branch by its unique identifier.
     *
     * @param branchId the unique identifier of the branch to retrieve
     * @return a Mono emitting the {@link BranchDTO} representing the branch if found,
     *         or an empty Mono if the branch does not exist
     */
    Mono<BranchDTO> getBranchById(Long branchId);
}