package com.catalis.core.organization.interfaces.dtos;

import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing a bank in the system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
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
    private Long countryId;

    @FilterableId
    private Long timeZoneId;

    private Boolean isActive;
    private LocalDateTime establishedAt;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}