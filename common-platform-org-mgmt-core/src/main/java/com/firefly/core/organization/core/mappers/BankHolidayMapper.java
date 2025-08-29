package com.firefly.core.organization.core.mappers;

import com.firefly.core.organization.interfaces.dtos.BankHolidayDTO;
import com.firefly.core.organization.models.entities.BankHoliday;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between BankHoliday entity and BankHolidayDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankHolidayMapper {

    /**
     * Converts a BankHoliday entity to a BankHolidayDTO.
     *
     * @param entity the BankHoliday entity to convert
     * @return the corresponding BankHolidayDTO
     */
    BankHolidayDTO toDTO(BankHoliday entity);

    /**
     * Converts a BankHolidayDTO to a BankHoliday entity.
     *
     * @param dto the BankHolidayDTO to convert
     * @return the corresponding BankHoliday entity
     */
    BankHoliday toEntity(BankHolidayDTO dto);
}