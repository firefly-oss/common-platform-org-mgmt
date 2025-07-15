package com.catalis.core.organization.interfaces.dtos;

import com.catalis.core.organization.interfaces.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing an audit log entry for bank-related actions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAuditLogDTO {
    
    private Long id;
    private Long bankId;
    private AuditAction action;
    private String entity;
    private String entityId;
    private String metadata;
    private String ipAddress;
    private Long userId;
    private LocalDateTime timestamp;
}