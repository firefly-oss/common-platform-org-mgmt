package com.catalis.core.organization.models.entities;

import com.catalis.core.organization.interfaces.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

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
    private Long id;
    
    @Column("branch_id")
    private Long branchId;
    
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
    private Long userId;
    
    @Column("timestamp")
    private LocalDateTime timestamp;
}