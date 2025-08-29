package com.firefly.core.organization.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entity representing a bank in the system.
 * Maps to the 'bank' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("bank")
public class Bank {
    
    @Id
    private Long id;
    
    @Column("code")
    private String code;
    
    @Column("name")
    private String name;
    
    @Column("description")
    private String description;
    
    @Column("logo_url")
    private String logoUrl;
    
    @Column("primary_color")
    private String primaryColor;
    
    @Column("secondary_color")
    private String secondaryColor;
    
    @Column("contact_email")
    private String contactEmail;
    
    @Column("contact_phone")
    private String contactPhone;
    
    @Column("website_url")
    private String websiteUrl;
    
    @Column("address_line")
    private String addressLine;
    
    @Column("postal_code")
    private String postalCode;
    
    @Column("city")
    private String city;
    
    @Column("state")
    private String state;
    
    @Column("country_id")
    private Long countryId;
    
    @Column("time_zone_id")
    private Long timeZoneId;
    
    @Column("is_active")
    private Boolean isActive;
    
    @Column("established_at")
    private LocalDateTime establishedAt;
    
    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;
    
    @Column("created_by")
    private Long createdBy;
    
    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;
    
    @Column("updated_by")
    private Long updatedBy;
}