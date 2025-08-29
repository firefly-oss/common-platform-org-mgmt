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
 * Entity representing a branch of a bank.
 * Maps to the 'branch' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("branch")
public class Branch {
    
    @Id
    private Long id;
    
    @Column("bank_id")
    private Long bankId;
    
    @Column("region_id")
    private Long regionId;
    
    @Column("code")
    private String code;
    
    @Column("name")
    private String name;
    
    @Column("description")
    private String description;
    
    @Column("phone_number")
    private String phoneNumber;
    
    @Column("email")
    private String email;
    
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
    
    @Column("latitude")
    private Float latitude;
    
    @Column("longitude")
    private Float longitude;
    
    @Column("is_active")
    private Boolean isActive;
    
    @Column("opened_at")
    private LocalDateTime openedAt;
    
    @Column("closed_at")
    private LocalDateTime closedAt;
    
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