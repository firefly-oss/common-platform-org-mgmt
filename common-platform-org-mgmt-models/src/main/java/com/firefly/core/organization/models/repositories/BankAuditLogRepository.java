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


package com.firefly.core.organization.models.repositories;

import com.firefly.core.organization.interfaces.enums.AuditAction;
import com.firefly.core.organization.models.entities.BankAuditLog;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for managing {@link BankAuditLog} entities.
 */
@Repository
public interface BankAuditLogRepository extends BaseRepository<BankAuditLog, UUID> {
    
    /**
     * Find all audit logs for a specific bank.
     *
     * @param bankId the bank ID
     * @return a Flux emitting all audit logs for the specified bank
     */
    Flux<BankAuditLog> findByBankId(UUID bankId);
    
    /**
     * Find all audit logs for a specific bank and action.
     *
     * @param bankId the bank ID
     * @param action the audit action
     * @return a Flux emitting all audit logs for the specified bank and action
     */
    Flux<BankAuditLog> findByBankIdAndAction(UUID bankId, AuditAction action);
    
    /**
     * Find all audit logs for a specific bank and entity.
     *
     * @param bankId the bank ID
     * @param entity the entity name
     * @return a Flux emitting all audit logs for the specified bank and entity
     */
    Flux<BankAuditLog> findByBankIdAndEntity(UUID bankId, String entity);
    
    /**
     * Find all audit logs for a specific bank, entity, and entity ID.
     *
     * @param bankId the bank ID
     * @param entity the entity name
     * @param entityId the entity ID
     * @return a Flux emitting all audit logs for the specified bank, entity, and entity ID
     */
    Flux<BankAuditLog> findByBankIdAndEntityAndEntityId(UUID bankId, String entity, String entityId);
    
    /**
     * Find all audit logs for a specific bank within a time range.
     *
     * @param bankId the bank ID
     * @param startTime the start time
     * @param endTime the end time
     * @return a Flux emitting all audit logs for the specified bank within the time range
     */
    Flux<BankAuditLog> findByBankIdAndTimestampBetween(UUID bankId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * Find all audit logs for a specific user.
     *
     * @param userId the user ID
     * @return a Flux emitting all audit logs for the specified user
     */
    Flux<BankAuditLog> findByUserId(UUID userId);
}