/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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
import java.util.UUID;

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
    private UUID id;
    
    @Column("bank_id")
    private UUID bankId;
    
    @Column("region_id")
    private UUID regionId;
    
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
    private UUID countryId;
    
    @Column("time_zone_id")
    private UUID timeZoneId;
    
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
    private UUID createdBy;
    
    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;
    
    @Column("updated_by")
    private UUID updatedBy;
}