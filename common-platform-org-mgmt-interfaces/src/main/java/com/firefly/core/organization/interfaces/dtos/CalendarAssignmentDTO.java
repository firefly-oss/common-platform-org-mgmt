package com.firefly.core.organization.interfaces.dtos;

import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO representing an assignment of a working calendar to a branch, department, or position.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarAssignmentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    private UUID calendarId;

    @FilterableId
    private UUID branchId;

    @FilterableId
    private UUID departmentId;

    @FilterableId
    private UUID positionId;

    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private UUID createdBy;
    private LocalDateTime updatedAt;
    private UUID updatedBy;
}