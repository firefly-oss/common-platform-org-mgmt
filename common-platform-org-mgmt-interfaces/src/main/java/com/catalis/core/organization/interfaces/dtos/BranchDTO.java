package com.catalis.core.organization.interfaces.dtos;

import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing a branch of a bank.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @FilterableId
    private Long bankId;

    @FilterableId
    private Long regionId;

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
    private Long countryId;

    @FilterableId
    private Long timeZoneId;

    private Float latitude;
    private Float longitude;
    private Boolean isActive;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}