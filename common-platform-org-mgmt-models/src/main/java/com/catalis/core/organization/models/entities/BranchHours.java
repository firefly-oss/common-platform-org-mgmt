package com.catalis.core.organization.models.entities;

import com.catalis.core.organization.interfaces.enums.DayOfWeek;
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
import java.time.LocalTime;

/**
 * Entity representing the operating hours of a branch for a specific day of the week.
 * Maps to the 'branch_hours' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("branch_hours")
public class BranchHours {
    
    @Id
    private Long id;
    
    @Column("branch_id")
    private Long branchId;
    
    @Column("day_of_week")
    private DayOfWeek dayOfWeek;
    
    @Column("open_time")
    private LocalTime openTime;
    
    @Column("close_time")
    private LocalTime closeTime;
    
    @Column("is_closed")
    private Boolean isClosed;
    
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