package com.catalis.core.organization.interfaces.dtos;

import com.catalis.core.organization.interfaces.enums.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO representing the operating hours of a branch for a specific day of the week.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchHoursDTO {
    
    private Long id;
    private Long branchId;
    private DayOfWeek dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean isClosed;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}