package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.interfaces.dtos.BranchHoursDTO;

import reactor.core.publisher.Mono;

/**
 * Service interface for managing branch hours.
 */
public interface BranchHoursService {
    /**
     * Filters the branch hours based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for BranchHoursDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of branch hours
     */
    Mono<PaginationResponse<BranchHoursDTO>> filterBranchHours(FilterRequest<BranchHoursDTO> filterRequest);
    
    /**
     * Creates new branch hours based on the provided information.
     *
     * @param branchHoursDTO the DTO object containing details of the branch hours to be created
     * @return a Mono that emits the created BranchHoursDTO object
     */
    Mono<BranchHoursDTO> createBranchHours(BranchHoursDTO branchHoursDTO);
    
    /**
     * Updates existing branch hours with updated information.
     *
     * @param branchHoursId the unique identifier of the branch hours to be updated
     * @param branchHoursDTO the data transfer object containing the updated details of the branch hours
     * @return a reactive Mono containing the updated BranchHoursDTO
     */
    Mono<BranchHoursDTO> updateBranchHours(Long branchHoursId, BranchHoursDTO branchHoursDTO);
    
    /**
     * Deletes branch hours identified by its unique ID.
     *
     * @param branchHoursId the unique identifier of the branch hours to be deleted
     * @return a Mono that completes when the branch hours are successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBranchHours(Long branchHoursId);
    
    /**
     * Retrieves branch hours by its unique identifier.
     *
     * @param branchHoursId the unique identifier of the branch hours to retrieve
     * @return a Mono emitting the {@link BranchHoursDTO} representing the branch hours if found,
     *         or an empty Mono if the branch hours do not exist
     */
    Mono<BranchHoursDTO> getBranchHoursById(Long branchHoursId);
}