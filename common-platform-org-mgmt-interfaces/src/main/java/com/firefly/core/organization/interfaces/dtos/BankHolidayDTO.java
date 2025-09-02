package com.firefly.core.organization.interfaces.dtos;

import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO representing a bank holiday.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankHolidayDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    private UUID bankId;

    @FilterableId
    private UUID branchId;

    @FilterableId
    private UUID countryId;

    private String name;
    private LocalDate date;
    private Boolean isRecurring;
    private String description;
    private LocalDateTime createdAt;
    private UUID createdBy;
    private LocalDateTime updatedAt;
    private UUID updatedBy;
}