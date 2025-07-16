package com.catalis.core.organization.interfaces.dtos;

import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @FilterableId
    private Long bankId;

    private String name;
    private String description;
    private Boolean isDefault;

    @FilterableId
    private Long timeZoneId;

    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}