package com.firefly.core.organization.interfaces.dtos;

import com.firefly.core.organization.interfaces.enums.AuditAction;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO representing an audit log entry for branch-related actions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchAuditLogDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    private UUID branchId;

    private AuditAction action;
    private String entity;
    private String entityId;
    private String metadata;
    private String ipAddress;

    @FilterableId
    private UUID userId;

    private LocalDateTime timestamp;
}