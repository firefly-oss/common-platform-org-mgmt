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
 * Entity representing a department within a branch.
 * Maps to the 'branch_department' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("branch_department")
public class BranchDepartment {
    
    @Id
    private UUID id;
    
    @Column("branch_id")
    private UUID branchId;
    
    @Column("name")
    private String name;
    
    @Column("description")
    private String description;
    
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