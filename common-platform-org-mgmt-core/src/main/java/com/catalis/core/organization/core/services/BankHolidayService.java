package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.interfaces.dtos.BankHolidayDTO;

import reactor.core.publisher.Mono;

/**
 * Service interface for managing bank holidays.
 */
public interface BankHolidayService {
    /**
     * Filters the bank holidays based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for BankHolidayDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of bank holidays
     */
    Mono<PaginationResponse<BankHolidayDTO>> filterBankHolidays(FilterRequest<BankHolidayDTO> filterRequest);
    
    /**
     * Creates a new bank holiday based on the provided information.
     *
     * @param bankHolidayDTO the DTO object containing details of the bank holiday to be created
     * @return a Mono that emits the created BankHolidayDTO object
     */
    Mono<BankHolidayDTO> createBankHoliday(BankHolidayDTO bankHolidayDTO);
    
    /**
     * Updates an existing bank holiday with updated information.
     *
     * @param bankHolidayId the unique identifier of the bank holiday to be updated
     * @param bankHolidayDTO the data transfer object containing the updated details of the bank holiday
     * @return a reactive Mono containing the updated BankHolidayDTO
     */
    Mono<BankHolidayDTO> updateBankHoliday(Long bankHolidayId, BankHolidayDTO bankHolidayDTO);
    
    /**
     * Deletes a bank holiday identified by its unique ID.
     *
     * @param bankHolidayId the unique identifier of the bank holiday to be deleted
     * @return a Mono that completes when the bank holiday is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBankHoliday(Long bankHolidayId);
    
    /**
     * Retrieves a bank holiday by its unique identifier.
     *
     * @param bankHolidayId the unique identifier of the bank holiday to retrieve
     * @return a Mono emitting the {@link BankHolidayDTO} representing the bank holiday if found,
     *         or an empty Mono if the bank holiday does not exist
     */
    Mono<BankHolidayDTO> getBankHolidayById(Long bankHolidayId);
}