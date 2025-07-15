package com.catalis.core.organization.core.mappers;

import com.catalis.core.organization.interfaces.dtos.BankAuditLogDTO;
import com.catalis.core.organization.models.entities.BankAuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between BankAuditLog entity and BankAuditLogDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankAuditLogMapper {

    /**
     * Converts a BankAuditLog entity to a BankAuditLogDTO.
     *
     * @param entity the BankAuditLog entity to convert
     * @return the corresponding BankAuditLogDTO
     */
    BankAuditLogDTO toDTO(BankAuditLog entity);

    /**
     * Converts a BankAuditLogDTO to a BankAuditLog entity.
     *
     * @param dto the BankAuditLogDTO to convert
     * @return the corresponding BankAuditLog entity
     */
    BankAuditLog toEntity(BankAuditLogDTO dto);
}