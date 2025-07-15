package com.catalis.core.organization.interfaces.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing a department within a branch.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDepartmentDTO {
    
    private Long id;
    private Long branchId;
    private String name;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}