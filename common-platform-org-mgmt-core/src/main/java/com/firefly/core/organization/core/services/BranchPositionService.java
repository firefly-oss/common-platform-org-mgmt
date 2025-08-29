package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.interfaces.dtos.BranchPositionDTO;

import reactor.core.publisher.Mono;

/**
 * Service interface for managing branch positions.
 */
public interface BranchPositionService {
    /**
     * Filters the branch positions based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for BranchPositionDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of branch positions
     */
    Mono<PaginationResponse<BranchPositionDTO>> filterBranchPositions(FilterRequest<BranchPositionDTO> filterRequest);

    /**
     * Filters the branch positions for a specific branch department based on the given criteria.
     *
     * @param branchId the unique identifier of the branch
     * @param departmentId the unique identifier of the department
     * @param filterRequest the request object containing filtering criteria for BranchPositionDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of branch positions
     */
    Mono<PaginationResponse<BranchPositionDTO>> filterBranchPositionsForDepartment(Long branchId, Long departmentId, FilterRequest<BranchPositionDTO> filterRequest);

    /**
     * Creates a new branch position based on the provided information.
     *
     * @param branchPositionDTO the DTO object containing details of the branch position to be created
     * @return a Mono that emits the created BranchPositionDTO object
     */
    Mono<BranchPositionDTO> createBranchPosition(BranchPositionDTO branchPositionDTO);

    /**
     * Creates a new branch position for a specific branch department based on the provided information.
     *
     * @param branchId the unique identifier of the branch
     * @param departmentId the unique identifier of the department
     * @param branchPositionDTO the DTO object containing details of the branch position to be created
     * @return a Mono that emits the created BranchPositionDTO object
     */
    Mono<BranchPositionDTO> createBranchPositionForDepartment(Long branchId, Long departmentId, BranchPositionDTO branchPositionDTO);

    /**
     * Updates an existing branch position with updated information.
     *
     * @param branchPositionId the unique identifier of the branch position to be updated
     * @param branchPositionDTO the data transfer object containing the updated details of the branch position
     * @return a reactive Mono containing the updated BranchPositionDTO
     */
    Mono<BranchPositionDTO> updateBranchPosition(Long branchPositionId, BranchPositionDTO branchPositionDTO);

    /**
     * Updates an existing branch position for a specific branch department with updated information.
     *
     * @param branchId the unique identifier of the branch
     * @param departmentId the unique identifier of the department
     * @param positionId the unique identifier of the position to be updated
     * @param branchPositionDTO the data transfer object containing the updated details of the branch position
     * @return a reactive Mono containing the updated BranchPositionDTO
     */
    Mono<BranchPositionDTO> updateBranchPositionForDepartment(Long branchId, Long departmentId, Long positionId, BranchPositionDTO branchPositionDTO);

    /**
     * Deletes a branch position identified by its unique ID.
     *
     * @param branchPositionId the unique identifier of the branch position to be deleted
     * @return a Mono that completes when the branch position is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBranchPosition(Long branchPositionId);

    /**
     * Deletes a branch position for a specific branch department identified by its unique ID.
     *
     * @param branchId the unique identifier of the branch
     * @param departmentId the unique identifier of the department
     * @param positionId the unique identifier of the position to be deleted
     * @return a Mono that completes when the branch position is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBranchPositionForDepartment(Long branchId, Long departmentId, Long positionId);

    /**
     * Retrieves a branch position by its unique identifier.
     *
     * @param branchPositionId the unique identifier of the branch position to retrieve
     * @return a Mono emitting the {@link BranchPositionDTO} representing the branch position if found,
     *         or an empty Mono if the branch position does not exist
     */
    Mono<BranchPositionDTO> getBranchPositionById(Long branchPositionId);

    /**
     * Retrieves a branch position for a specific branch department by its unique identifier.
     *
     * @param branchId the unique identifier of the branch
     * @param departmentId the unique identifier of the department
     * @param positionId the unique identifier of the position to retrieve
     * @return a Mono emitting the {@link BranchPositionDTO} representing the branch position if found,
     *         or an empty Mono if the branch position does not exist or doesn't belong to the specified department
     */
    Mono<BranchPositionDTO> getBranchPositionByIdForDepartment(Long branchId, Long departmentId, Long positionId);
}
