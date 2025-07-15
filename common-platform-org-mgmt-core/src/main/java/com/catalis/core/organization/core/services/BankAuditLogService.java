package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.interfaces.dtos.BankAuditLogDTO;

import reactor.core.publisher.Mono;

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
    Mono<BankAuditLogDTO> updateBankAuditLog(Long bankAuditLogId, BankAuditLogDTO bankAuditLogDTO);
    
    /**
     * Deletes a bank audit log identified by its unique ID.
     *
     * @param bankAuditLogId the unique identifier of the bank audit log to be deleted
     * @return a Mono that completes when the bank audit log is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBankAuditLog(Long bankAuditLogId);
    
    /**
     * Retrieves a bank audit log by its unique identifier.
     *
     * @param bankAuditLogId the unique identifier of the bank audit log to retrieve
     * @return a Mono emitting the {@link BankAuditLogDTO} representing the bank audit log if found,
     *         or an empty Mono if the bank audit log does not exist
     */
    Mono<BankAuditLogDTO> getBankAuditLogById(Long bankAuditLogId);
}