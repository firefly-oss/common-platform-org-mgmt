package com.catalis.core.organization.core.mappers;

import com.catalis.core.organization.interfaces.dtos.BranchAuditLogDTO;
import com.catalis.core.organization.models.entities.BranchAuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between BranchAuditLog entity and BranchAuditLogDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BranchAuditLogMapper {

    /**
     * Converts a BranchAuditLog entity to a BranchAuditLogDTO.
     *
     * @param entity the BranchAuditLog entity to convert
     * @return the corresponding BranchAuditLogDTO
     */
    BranchAuditLogDTO toDTO(BranchAuditLog entity);

    /**
     * Converts a BranchAuditLogDTO to a BranchAuditLog entity.
     *
     * @param dto the BranchAuditLogDTO to convert
     * @return the corresponding BranchAuditLog entity
     */
    BranchAuditLog toEntity(BranchAuditLogDTO dto);
}