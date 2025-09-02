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
 * DTO representing a branch of a bank.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    private UUID bankId;

    @FilterableId
    private UUID regionId;

    private String code;
    private String name;
    private String description;
    private String phoneNumber;
    private String email;
    private String addressLine;
    private String postalCode;
    private String city;
    private String state;

    @FilterableId
    private UUID countryId;

    @FilterableId
    private UUID timeZoneId;

    private Float latitude;
    private Float longitude;
    private Boolean isActive;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private LocalDateTime createdAt;
    private UUID createdBy;
    private LocalDateTime updatedAt;
    private UUID updatedBy;
}