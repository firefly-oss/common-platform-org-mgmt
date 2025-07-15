package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.interfaces.dtos.BankDTO;

import reactor.core.publisher.Mono;

/**
 * Service interface for managing banks.
 */
public interface BankService {
    /**
     * Filters the banks based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for BankDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of banks
     */
    Mono<PaginationResponse<BankDTO>> filterBanks(FilterRequest<BankDTO> filterRequest);
    
    /**
     * Creates a new bank based on the provided information.
     *
     * @param bankDTO the DTO object containing details of the bank to be created
     * @return a Mono that emits the created BankDTO object
     */
    Mono<BankDTO> createBank(BankDTO bankDTO);
    
    /**
     * Updates an existing bank with updated information.
     *
     * @param bankId the unique identifier of the bank to be updated
     * @param bankDTO the data transfer object containing the updated details of the bank
     * @return a reactive Mono containing the updated BankDTO
     */
    Mono<BankDTO> updateBank(Long bankId, BankDTO bankDTO);
    
    /**
     * Deletes a bank identified by its unique ID.
     *
     * @param bankId the unique identifier of the bank to be deleted
     * @return a Mono that completes when the bank is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBank(Long bankId);
    
    /**
     * Retrieves a bank by its unique identifier.
     *
     * @param bankId the unique identifier of the bank to retrieve
     * @return a Mono emitting the {@link BankDTO} representing the bank if found,
     *         or an empty Mono if the bank does not exist
     */
    Mono<BankDTO> getBankById(Long bankId);
}