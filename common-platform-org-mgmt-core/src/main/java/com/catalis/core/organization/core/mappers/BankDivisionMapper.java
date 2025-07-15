package com.catalis.core.organization.core.mappers;

import com.catalis.core.organization.interfaces.dtos.BankDivisionDTO;
import com.catalis.core.organization.models.entities.BankDivision;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between BankDivision entity and BankDivisionDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankDivisionMapper {

    /**
     * Converts a BankDivision entity to a BankDivisionDTO.
     *
     * @param entity the BankDivision entity to convert
     * @return the corresponding BankDivisionDTO
     */
    BankDivisionDTO toDTO(BankDivision entity);

    /**
     * Converts a BankDivisionDTO to a BankDivision entity.
     *
     * @param dto the BankDivisionDTO to convert
     * @return the corresponding BankDivision entity
     */
    BankDivision toEntity(BankDivisionDTO dto);
}