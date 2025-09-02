package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.interfaces.dtos.BankDivisionDTO;

import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing bank divisions.
 */
public interface BankDivisionService {
    /**
     * Filters the bank divisions based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for BankDivisionDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of bank divisions
     */
    Mono<PaginationResponse<BankDivisionDTO>> filterBankDivisions(FilterRequest<BankDivisionDTO> filterRequest);

    /**
     * Creates a new bank division based on the provided information.
     *
     * @param bankDivisionDTO the DTO object containing details of the bank division to be created
     * @return a Mono that emits the created BankDivisionDTO object
     */
    Mono<BankDivisionDTO> createBankDivision(BankDivisionDTO bankDivisionDTO);

    /**
     * Updates an existing bank division with updated information.
     *
     * @param bankDivisionId the unique identifier of the bank division to be updated
     * @param bankDivisionDTO the data transfer object containing the updated details of the bank division
     * @return a reactive Mono containing the updated BankDivisionDTO
     */
    Mono<BankDivisionDTO> updateBankDivision(UUID bankDivisionId, BankDivisionDTO bankDivisionDTO);

    /**
     * Deletes a bank division identified by its unique ID.
     *
     * @param bankDivisionId the unique identifier of the bank division to be deleted
     * @return a Mono that completes when the bank division is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBankDivision(UUID bankDivisionId);

    /**
     * Retrieves a bank division by its unique identifier.
     *
     * @param bankDivisionId the unique identifier of the bank division to retrieve
     * @return a Mono emitting the {@link BankDivisionDTO} representing the bank division if found,
     *         or an empty Mono if the bank division does not exist
     */
    Mono<BankDivisionDTO> getBankDivisionById(UUID bankDivisionId);

    /**
     * Retrieves a bank division for a specific bank by its unique identifier.
     *
     * @param bankId the unique identifier of the bank
     * @param divisionId the unique identifier of the division to retrieve
     * @return a Mono emitting the {@link BankDivisionDTO} representing the division if found,
     *         or an empty Mono if the division does not exist or doesn't belong to the specified bank
     */
    Mono<BankDivisionDTO> getBankDivisionByIdForBank(UUID bankId, UUID divisionId);

    /**
     * Updates an existing bank division for a specific bank with updated information.
     *
     * @param bankId the unique identifier of the bank
     * @param divisionId the unique identifier of the division to be updated
     * @param bankDivisionDTO the data transfer object containing the updated details of the bank division
     * @return a reactive Mono containing the updated BankDivisionDTO
     */
    Mono<BankDivisionDTO> updateBankDivisionForBank(UUID bankId, UUID divisionId, BankDivisionDTO bankDivisionDTO);

    /**
     * Deletes a bank division for a specific bank identified by its unique ID.
     *
     * @param bankId the unique identifier of the bank
     * @param divisionId the unique identifier of the division to be deleted
     * @return a Mono that completes when the bank division is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBankDivisionForBank(UUID bankId, UUID divisionId);
}
