package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.interfaces.dtos.BankRegionDTO;

import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing bank regions.
 */
public interface BankRegionService {
    /**
     * Filters the bank regions based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for BankRegionDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of bank regions
     */
    Mono<PaginationResponse<BankRegionDTO>> filterBankRegions(FilterRequest<BankRegionDTO> filterRequest);

    /**
     * Filters the bank regions for a specific bank division based on the given criteria.
     *
     * @param bankId the unique identifier of the bank
     * @param divisionId the unique identifier of the division
     * @param filterRequest the request object containing filtering criteria for BankRegionDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of bank regions
     */
    Mono<PaginationResponse<BankRegionDTO>> filterBankRegionsForDivision(UUID bankId, UUID divisionId, FilterRequest<BankRegionDTO> filterRequest);

    /**
     * Creates a new bank region based on the provided information.
     *
     * @param bankRegionDTO the DTO object containing details of the bank region to be created
     * @return a Mono that emits the created BankRegionDTO object
     */
    Mono<BankRegionDTO> createBankRegion(BankRegionDTO bankRegionDTO);

    /**
     * Creates a new bank region for a specific bank division based on the provided information.
     *
     * @param bankId the unique identifier of the bank
     * @param divisionId the unique identifier of the division
     * @param bankRegionDTO the DTO object containing details of the bank region to be created
     * @return a Mono that emits the created BankRegionDTO object
     */
    Mono<BankRegionDTO> createBankRegionForDivision(UUID bankId, UUID divisionId, BankRegionDTO bankRegionDTO);

    /**
     * Updates an existing bank region with updated information.
     *
     * @param bankRegionId the unique identifier of the bank region to be updated
     * @param bankRegionDTO the data transfer object containing the updated details of the bank region
     * @return a reactive Mono containing the updated BankRegionDTO
     */
    Mono<BankRegionDTO> updateBankRegion(UUID bankRegionId, BankRegionDTO bankRegionDTO);

    /**
     * Updates an existing bank region for a specific bank division with updated information.
     *
     * @param bankId the unique identifier of the bank
     * @param divisionId the unique identifier of the division
     * @param regionId the unique identifier of the region to be updated
     * @param bankRegionDTO the data transfer object containing the updated details of the bank region
     * @return a reactive Mono containing the updated BankRegionDTO
     */
    Mono<BankRegionDTO> updateBankRegionForDivision(UUID bankId, UUID divisionId, UUID regionId, BankRegionDTO bankRegionDTO);

    /**
     * Deletes a bank region identified by its unique ID.
     *
     * @param bankRegionId the unique identifier of the bank region to be deleted
     * @return a Mono that completes when the bank region is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBankRegion(UUID bankRegionId);

    /**
     * Deletes a bank region for a specific bank division identified by its unique ID.
     *
     * @param bankId the unique identifier of the bank
     * @param divisionId the unique identifier of the division
     * @param regionId the unique identifier of the region to be deleted
     * @return a Mono that completes when the bank region is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBankRegionForDivision(UUID bankId, UUID divisionId, UUID regionId);

    /**
     * Retrieves a bank region by its unique identifier.
     *
     * @param bankRegionId the unique identifier of the bank region to retrieve
     * @return a Mono emitting the {@link BankRegionDTO} representing the bank region if found,
     *         or an empty Mono if the bank region does not exist
     */
    Mono<BankRegionDTO> getBankRegionById(UUID bankRegionId);

    /**
     * Retrieves a bank region for a specific bank division by its unique identifier.
     *
     * @param bankId the unique identifier of the bank
     * @param divisionId the unique identifier of the division
     * @param regionId the unique identifier of the region to retrieve
     * @return a Mono emitting the {@link BankRegionDTO} representing the bank region if found,
     *         or an empty Mono if the bank region does not exist or doesn't belong to the specified division
     */
    Mono<BankRegionDTO> getBankRegionByIdForDivision(UUID bankId, UUID divisionId, UUID regionId);
}
