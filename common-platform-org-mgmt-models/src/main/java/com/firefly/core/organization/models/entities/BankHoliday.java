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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a bank holiday.
 * Maps to the 'bank_holiday' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("bank_holiday")
public class BankHoliday {
    
    @Id
    private UUID id;
    
    @Column("bank_id")
    private UUID bankId;
    
    @Column("branch_id")
    private UUID branchId;
    
    @Column("country_id")
    private UUID countryId;
    
    @Column("name")
    private String name;
    
    @Column("date")
    private LocalDate date;
    
    @Column("is_recurring")
    private Boolean isRecurring;
    
    @Column("description")
    private String description;
    
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