/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.interfaces.dtos.BankHolidayDTO;

import reactor.core.publisher.Mono;
import java.util.UUID;

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
    Mono<BankHolidayDTO> updateBankHoliday(UUID bankHolidayId, BankHolidayDTO bankHolidayDTO);
    
    /**
     * Deletes a bank holiday identified by its unique ID.
     *
     * @param bankHolidayId the unique identifier of the bank holiday to be deleted
     * @return a Mono that completes when the bank holiday is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBankHoliday(UUID bankHolidayId);
    
    /**
     * Retrieves a bank holiday by its unique identifier.
     *
     * @param bankHolidayId the unique identifier of the bank holiday to retrieve
     * @return a Mono emitting the {@link BankHolidayDTO} representing the bank holiday if found,
     *         or an empty Mono if the bank holiday does not exist
     */
    Mono<BankHolidayDTO> getBankHolidayById(UUID bankHolidayId);
}