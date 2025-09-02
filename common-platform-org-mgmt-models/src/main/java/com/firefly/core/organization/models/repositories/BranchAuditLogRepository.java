package com.firefly.core.organization.models.repositories;

import com.firefly.core.organization.interfaces.enums.AuditAction;
import com.firefly.core.organization.models.entities.BranchAuditLog;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for managing {@link BranchAuditLog} entities.
 */
@Repository
public interface BranchAuditLogRepository extends BaseRepository<BranchAuditLog, UUID> {
    
    /**
     * Find all audit logs for a specific branch.
     *
     * @param branchId the branch ID
     * @return a Flux emitting all audit logs for the specified branch
     */
    Flux<BranchAuditLog> findByBranchId(UUID branchId);
    
    /**
     * Find all audit logs for a specific branch and action.
     *
     * @param branchId the branch ID
     * @param action the audit action
     * @return a Flux emitting all audit logs for the specified branch and action
     */
    Flux<BranchAuditLog> findByBranchIdAndAction(UUID branchId, AuditAction action);
    
    /**
     * Find all audit logs for a specific branch and entity.
     *
     * @param branchId the branch ID
     * @param entity the entity name
     * @return a Flux emitting all audit logs for the specified branch and entity
     */
    Flux<BranchAuditLog> findByBranchIdAndEntity(UUID branchId, String entity);
    
    /**
     * Find all audit logs for a specific branch, entity, and entity ID.
     *
     * @param branchId the branch ID
     * @param entity the entity name
     * @param entityId the entity ID
     * @return a Flux emitting all audit logs for the specified branch, entity, and entity ID
     */
    Flux<BranchAuditLog> findByBranchIdAndEntityAndEntityId(UUID branchId, String entity, String entityId);
    
    /**
     * Find all audit logs for a specific branch within a time range.
     *
     * @param branchId the branch ID
     * @param startTime the start time
     * @param endTime the end time
     * @return a Flux emitting all audit logs for the specified branch within the time range
     */
    Flux<BranchAuditLog> findByBranchIdAndTimestampBetween(UUID branchId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * Find all audit logs for a specific user.
     *
     * @param userId the user ID
     * @return a Flux emitting all audit logs for the specified user
     */
    Flux<BranchAuditLog> findByUserId(UUID userId);
}