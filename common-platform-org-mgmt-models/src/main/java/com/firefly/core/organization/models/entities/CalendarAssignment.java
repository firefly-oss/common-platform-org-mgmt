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
 * Entity representing an assignment of a working calendar to a branch, department, or position.
 * Maps to the 'calendar_assignment' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("calendar_assignment")
public class CalendarAssignment {
    
    @Id
    private UUID id;
    
    @Column("calendar_id")
    private UUID calendarId;
    
    @Column("branch_id")
    private UUID branchId;
    
    @Column("department_id")
    private UUID departmentId;
    
    @Column("position_id")
    private UUID positionId;
    
    @Column("effective_from")
    private LocalDateTime effectiveFrom;
    
    @Column("effective_to")
    private LocalDateTime effectiveTo;
    
    @Column("is_active")
    private Boolean isActive;
    
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