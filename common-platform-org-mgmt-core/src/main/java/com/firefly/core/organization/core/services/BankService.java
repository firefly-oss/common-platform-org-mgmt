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
import com.firefly.core.organization.interfaces.dtos.BankDTO;

import reactor.core.publisher.Mono;
import java.util.UUID;

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
    Mono<BankDTO> updateBank(UUID bankId, BankDTO bankDTO);
    
    /**
     * Deletes a bank identified by its unique ID.
     *
     * @param bankId the unique identifier of the bank to be deleted
     * @return a Mono that completes when the bank is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBank(UUID bankId);
    
    /**
     * Retrieves a bank by its unique identifier.
     *
     * @param bankId the unique identifier of the bank to retrieve
     * @return a Mono emitting the {@link BankDTO} representing the bank if found,
     *         or an empty Mono if the bank does not exist
     */
    Mono<BankDTO> getBankById(UUID bankId);
}