package com.catalis.core.organization.core.mappers;

import com.catalis.core.organization.interfaces.dtos.BankRegionDTO;
import com.catalis.core.organization.models.entities.BankRegion;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between BankRegion entity and BankRegionDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankRegionMapper {

    /**
     * Converts a BankRegion entity to a BankRegionDTO.
     *
     * @param entity the BankRegion entity to convert
     * @return the corresponding BankRegionDTO
     */
    BankRegionDTO toDTO(BankRegion entity);

    /**
     * Converts a BankRegionDTO to a BankRegion entity.
     *
     * @param dto the BankRegionDTO to convert
     * @return the corresponding BankRegion entity
     */
    BankRegion toEntity(BankRegionDTO dto);
}