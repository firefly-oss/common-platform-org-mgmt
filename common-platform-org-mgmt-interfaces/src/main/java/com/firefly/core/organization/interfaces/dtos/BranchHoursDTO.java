package com.firefly.core.organization.interfaces.dtos;

import com.firefly.core.organization.interfaces.enums.DayOfWeek;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

/**
 * DTO representing the operating hours of a branch for a specific day of the week.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchHoursDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    private UUID branchId;

    private DayOfWeek dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean isClosed;
    private LocalDateTime createdAt;
    private UUID createdBy;
    private LocalDateTime updatedAt;
    private UUID updatedBy;
}