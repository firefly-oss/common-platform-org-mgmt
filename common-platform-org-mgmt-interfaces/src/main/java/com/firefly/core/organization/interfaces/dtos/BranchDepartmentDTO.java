package com.firefly.core.organization.interfaces.dtos;

import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO representing a department within a branch.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDepartmentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    private UUID branchId;

    private String name;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private UUID createdBy;
    private LocalDateTime updatedAt;
    private UUID updatedBy;
}