package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.interfaces.dtos.BranchAuditLogDTO;

import reactor.core.publisher.Mono;

/**
 * Service interface for managing branch audit logs.
 */
public interface BranchAuditLogService {
    /**
     * Filters the branch audit logs based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for BranchAuditLogDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of branch audit logs
     */
    Mono<PaginationResponse<BranchAuditLogDTO>> filterBranchAuditLogs(FilterRequest<BranchAuditLogDTO> filterRequest);
    
    /**
     * Creates a new branch audit log based on the provided information.
     *
     * @param branchAuditLogDTO the DTO object containing details of the branch audit log to be created
     * @return a Mono that emits the created BranchAuditLogDTO object
     */
    Mono<BranchAuditLogDTO> createBranchAuditLog(BranchAuditLogDTO branchAuditLogDTO);
    
    /**
     * Updates an existing branch audit log with updated information.
     *
     * @param branchAuditLogId the unique identifier of the branch audit log to be updated
     * @param branchAuditLogDTO the data transfer object containing the updated details of the branch audit log
     * @return a reactive Mono containing the updated BranchAuditLogDTO
     */
    Mono<BranchAuditLogDTO> updateBranchAuditLog(Long branchAuditLogId, BranchAuditLogDTO branchAuditLogDTO);
    
    /**
     * Deletes a branch audit log identified by its unique ID.
     *
     * @param branchAuditLogId the unique identifier of the branch audit log to be deleted
     * @return a Mono that completes when the branch audit log is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteBranchAuditLog(Long branchAuditLogId);
    
    /**
     * Retrieves a branch audit log by its unique identifier.
     *
     * @param branchAuditLogId the unique identifier of the branch audit log to retrieve
     * @return a Mono emitting the {@link BranchAuditLogDTO} representing the branch audit log if found,
     *         or an empty Mono if the branch audit log does not exist
     */
    Mono<BranchAuditLogDTO> getBranchAuditLogById(Long branchAuditLogId);
}