package com.catalis.core.organization.interfaces.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing a region within a bank division.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankRegionDTO {
    
    private Long id;
    private Long divisionId;
    private String code;
    private String name;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}