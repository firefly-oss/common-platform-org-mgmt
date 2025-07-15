package com.catalis.core.organization.interfaces.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO representing a bank holiday.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankHolidayDTO {
    
    private Long id;
    private Long bankId;
    private Long branchId;
    private Long countryId;
    private String name;
    private LocalDate date;
    private Boolean isRecurring;
    private String description;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}