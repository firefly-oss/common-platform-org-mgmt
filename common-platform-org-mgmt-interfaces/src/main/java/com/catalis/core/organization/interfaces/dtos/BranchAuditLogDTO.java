package com.catalis.core.organization.interfaces.dtos;

import com.catalis.core.organization.interfaces.enums.AuditAction;
import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing an audit log entry for branch-related actions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchAuditLogDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @FilterableId
    private Long branchId;

    private AuditAction action;
    private String entity;
    private String entityId;
    private String metadata;
    private String ipAddress;

    @FilterableId
    private Long userId;

    private LocalDateTime timestamp;
}