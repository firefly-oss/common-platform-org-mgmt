package com.firefly.core.organization.models.repositories;

import com.firefly.core.organization.interfaces.enums.AuditAction;
import com.firefly.core.organization.models.entities.BankAuditLog;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

/**
 * Repository for managing {@link BankAuditLog} entities.
 */
@Repository
public interface BankAuditLogRepository extends BaseRepository<BankAuditLog, Long> {
    
    /**
     * Find all audit logs for a specific bank.
     *
     * @param bankId the bank ID
     * @return a Flux emitting all audit logs for the specified bank
     */
    Flux<BankAuditLog> findByBankId(Long bankId);
    
    /**
     * Find all audit logs for a specific bank and action.
     *
     * @param bankId the bank ID
     * @param action the audit action
     * @return a Flux emitting all audit logs for the specified bank and action
     */
    Flux<BankAuditLog> findByBankIdAndAction(Long bankId, AuditAction action);
    
    /**
     * Find all audit logs for a specific bank and entity.
     *
     * @param bankId the bank ID
     * @param entity the entity name
     * @return a Flux emitting all audit logs for the specified bank and entity
     */
    Flux<BankAuditLog> findByBankIdAndEntity(Long bankId, String entity);
    
    /**
     * Find all audit logs for a specific bank, entity, and entity ID.
     *
     * @param bankId the bank ID
     * @param entity the entity name
     * @param entityId the entity ID
     * @return a Flux emitting all audit logs for the specified bank, entity, and entity ID
     */
    Flux<BankAuditLog> findByBankIdAndEntityAndEntityId(Long bankId, String entity, String entityId);
    
    /**
     * Find all audit logs for a specific bank within a time range.
     *
     * @param bankId the bank ID
     * @param startTime the start time
     * @param endTime the end time
     * @return a Flux emitting all audit logs for the specified bank within the time range
     */
    Flux<BankAuditLog> findByBankIdAndTimestampBetween(Long bankId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * Find all audit logs for a specific user.
     *
     * @param userId the user ID
     * @return a Flux emitting all audit logs for the specified user
     */
    Flux<BankAuditLog> findByUserId(Long userId);
}