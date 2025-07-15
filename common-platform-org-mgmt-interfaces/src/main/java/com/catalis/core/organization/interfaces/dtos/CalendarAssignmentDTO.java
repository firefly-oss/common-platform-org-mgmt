package com.catalis.core.organization.interfaces.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing an assignment of a working calendar to a branch, department, or position.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarAssignmentDTO {
    
    private Long id;
    private Long calendarId;
    private Long branchId;
    private Long departmentId;
    private Long positionId;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}