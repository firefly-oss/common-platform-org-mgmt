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
 * DTO representing a bank in the system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private String code;
    private String name;
    private String description;
    private String logoUrl;
    private String primaryColor;
    private String secondaryColor;
    private String contactEmail;
    private String contactPhone;
    private String websiteUrl;
    private String addressLine;
    private String postalCode;
    private String city;
    private String state;

    @FilterableId
    private UUID countryId;

    @FilterableId
    private UUID timeZoneId;

    private Boolean isActive;
    private LocalDateTime establishedAt;
    private LocalDateTime createdAt;
    private UUID createdBy;
    private LocalDateTime updatedAt;
    private UUID updatedBy;
}