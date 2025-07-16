package com.catalis.core.organization.interfaces.dtos;

import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @FilterableId
    private Long bankId;

    @FilterableId
    private Long branchId;

    @FilterableId
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