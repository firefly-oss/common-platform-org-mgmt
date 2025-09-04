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


package com.firefly.core.organization.models.entities;

import com.firefly.core.organization.interfaces.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing an audit log entry for branch-related actions.
 * Maps to the 'branch_audit_log' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("branch_audit_log")
public class BranchAuditLog {
    
    @Id
    private UUID id;
    
    @Column("branch_id")
    private UUID branchId;
    
    @Column("action")
    private AuditAction action;
    
    @Column("entity")
    private String entity;
    
    @Column("entity_id")
    private String entityId;
    
    @Column("metadata")
    private String metadata;
    
    @Column("ip_address")
    private String ipAddress;
    
    @Column("user_id")
    private UUID userId;
    
    @Column("timestamp")
    private LocalDateTime timestamp;
}