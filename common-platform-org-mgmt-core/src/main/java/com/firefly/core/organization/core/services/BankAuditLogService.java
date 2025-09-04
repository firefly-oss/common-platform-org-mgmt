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
import com.firefly.core.organization.interfaces.dtos.BankAuditLogDTO;

import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing bank audit logs.
 */
public interface BankAuditLogService {
    /**
     * Filters the bank audit logs based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for BankAuditLogDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of bank audit logs
     */
    Mono<PaginationResponse<BankAuditLogDTO>> filterBankAuditLogs(FilterRequest<BankAuditLogDTO> filterRequest);
    
    /**
     * Creates a new bank audit log based on the provided information.
     *
     * @param bankAuditLogDTO the DTO object containing details of the bank audit log to be created
     * @return a Mono that emits the created BankAuditLogDTO object
     */
    Mono<BankAuditLogDTO> createBankAuditLog(BankAuditLogDTO bankAuditLogDTO);
    
    /**
     * Updates an existing bank audit log with updated information.
     *
     * @param bankAuditLogId the unique identifier of the bank audit log to be updated
     * @param bankAuditLogDTO the data transfer object containing the updated details of the bank audit log
     * @return a reactive Mono containing the updated BankAuditLogDTO
     */
    Mono<BankAuditLogDTO> updateBankAuditLog(UUID bankAuditLogId, BankAuditLogDTO bankAuditLogDTO);
    
    /**
     * Deletes a bank audit log identified by its unique ID.
     *
     * @param bankAuditLogId the unique identifier of the bank audit log to be deleted
     * @return a Mono that completes when the bank audit log is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBankAuditLog(UUID bankAuditLogId);
    
    /**
     * Retrieves a bank audit log by its unique identifier.
     *
     * @param bankAuditLogId the unique identifier of the bank audit log to retrieve
     * @return a Mono emitting the {@link BankAuditLogDTO} representing the bank audit log if found,
     *         or an empty Mono if the bank audit log does not exist
     */
    Mono<BankAuditLogDTO> getBankAuditLogById(UUID bankAuditLogId);
}