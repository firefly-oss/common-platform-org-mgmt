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

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Long id;
    
    @Column("bank_id")
    private Long bankId;
    
    @Column("branch_id")
    private Long branchId;
    
    @Column("country_id")
    private Long countryId;
    
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
    private Long createdBy;
    
    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;
    
    @Column("updated_by")
    private Long updatedBy;
}