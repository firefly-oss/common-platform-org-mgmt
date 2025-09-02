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
 * Entity representing an audit log entry for bank-related actions.
 * Maps to the 'bank_audit_log' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("bank_audit_log")
public class BankAuditLog {
    
    @Id
    private UUID id;
    
    @Column("bank_id")
    private UUID bankId;
    
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