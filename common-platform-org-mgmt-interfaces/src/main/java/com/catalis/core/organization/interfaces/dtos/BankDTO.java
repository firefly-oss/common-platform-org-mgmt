package com.catalis.core.organization.interfaces.dtos;

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
    private Long countryId;
    private Long timeZoneId;
    private Boolean isActive;
    private LocalDateTime establishedAt;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}