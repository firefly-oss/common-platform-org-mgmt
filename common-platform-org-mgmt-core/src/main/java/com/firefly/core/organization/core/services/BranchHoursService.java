package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.interfaces.dtos.BranchHoursDTO;

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
     * Filters the branch hours for a specific branch based on the given criteria.
     *
     * @param branchId the unique identifier of the branch
     * @param filterRequest the request object containing filtering criteria for BranchHoursDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of branch hours
     */
    Mono<PaginationResponse<BranchHoursDTO>> filterBranchHoursForBranch(Long branchId, FilterRequest<BranchHoursDTO> filterRequest);

    /**
     * Creates new branch hours based on the provided information.
     *
     * @param branchHoursDTO the DTO object containing details of the branch hours to be created
     * @return a Mono that emits the created BranchHoursDTO object
     */
    Mono<BranchHoursDTO> createBranchHours(BranchHoursDTO branchHoursDTO);

    /**
     * Creates new branch hours for a specific branch based on the provided information.
     *
     * @param branchId the unique identifier of the branch
     * @param branchHoursDTO the DTO object containing details of the branch hours to be created
     * @return a Mono that emits the created BranchHoursDTO object
     */
    Mono<BranchHoursDTO> createBranchHoursForBranch(Long branchId, BranchHoursDTO branchHoursDTO);

    /**
     * Updates existing branch hours with updated information.
     *
     * @param branchHoursId the unique identifier of the branch hours to be updated
     * @param branchHoursDTO the data transfer object containing the updated details of the branch hours
     * @return a reactive Mono containing the updated BranchHoursDTO
     */
    Mono<BranchHoursDTO> updateBranchHours(Long branchHoursId, BranchHoursDTO branchHoursDTO);

    /**
     * Updates existing branch hours for a specific branch with updated information.
     *
     * @param branchId the unique identifier of the branch
     * @param hoursId the unique identifier of the hours to be updated
     * @param branchHoursDTO the data transfer object containing the updated details of the branch hours
     * @return a reactive Mono containing the updated BranchHoursDTO
     */
    Mono<BranchHoursDTO> updateBranchHoursForBranch(Long branchId, Long hoursId, BranchHoursDTO branchHoursDTO);

    /**
     * Deletes branch hours identified by its unique ID.
     *
     * @param branchHoursId the unique identifier of the branch hours to be deleted
     * @return a Mono that completes when the branch hours are successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBranchHours(Long branchHoursId);

    /**
     * Deletes branch hours for a specific branch identified by its unique ID.
     *
     * @param branchId the unique identifier of the branch
     * @param hoursId the unique identifier of the hours to be deleted
     * @return a Mono that completes when the branch hours are successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBranchHoursForBranch(Long branchId, Long hoursId);

    /**
     * Retrieves branch hours by its unique identifier.
     *
     * @param branchHoursId the unique identifier of the branch hours to retrieve
     * @return a Mono emitting the {@link BranchHoursDTO} representing the branch hours if found,
     *         or an empty Mono if the branch hours do not exist
     */
    Mono<BranchHoursDTO> getBranchHoursById(Long branchHoursId);

    /**
     * Retrieves branch hours for a specific branch by its unique identifier.
     *
     * @param branchId the unique identifier of the branch
     * @param hoursId the unique identifier of the hours to retrieve
     * @return a Mono emitting the {@link BranchHoursDTO} representing the branch hours if found,
     *         or an empty Mono if the branch hours do not exist or don't belong to the specified branch
     */
    Mono<BranchHoursDTO> getBranchHoursByIdForBranch(Long branchId, Long hoursId);
}
