package com.catalis.core.organization.interfaces.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing a working calendar for a bank.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkingCalendarDTO {
    
    private Long id;
    private Long bankId;
    private String name;
    private String description;
    private Boolean isDefault;
    private Long timeZoneId;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}