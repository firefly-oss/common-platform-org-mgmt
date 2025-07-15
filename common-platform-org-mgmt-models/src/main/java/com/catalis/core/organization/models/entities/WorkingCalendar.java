package com.catalis.core.organization.models.entities;

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
 * Entity representing a working calendar for a bank.
 * Maps to the 'working_calendar' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("working_calendar")
public class WorkingCalendar {
    
    @Id
    private Long id;
    
    @Column("bank_id")
    private Long bankId;
    
    @Column("name")
    private String name;
    
    @Column("description")
    private String description;
    
    @Column("is_default")
    private Boolean isDefault;
    
    @Column("time_zone_id")
    private Long timeZoneId;
    
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