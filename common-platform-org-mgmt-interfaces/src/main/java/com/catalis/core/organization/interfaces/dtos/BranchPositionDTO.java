package com.catalis.core.organization.interfaces.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing a position within a branch department.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchPositionDTO {
    
    private Long id;
    private Long departmentId;
    private String title;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}