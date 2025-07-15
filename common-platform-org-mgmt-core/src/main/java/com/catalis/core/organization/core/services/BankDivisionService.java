package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.interfaces.dtos.BankDivisionDTO;

import reactor.core.publisher.Mono;

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
    Mono<BankDivisionDTO> updateBankDivision(Long bankDivisionId, BankDivisionDTO bankDivisionDTO);
    
    /**
     * Deletes a bank division identified by its unique ID.
     *
     * @param bankDivisionId the unique identifier of the bank division to be deleted
     * @return a Mono that completes when the bank division is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBankDivision(Long bankDivisionId);
    
    /**
     * Retrieves a bank division by its unique identifier.
     *
     * @param bankDivisionId the unique identifier of the bank division to retrieve
     * @return a Mono emitting the {@link BankDivisionDTO} representing the bank division if found,
     *         or an empty Mono if the bank division does not exist
     */
    Mono<BankDivisionDTO> getBankDivisionById(Long bankDivisionId);
}