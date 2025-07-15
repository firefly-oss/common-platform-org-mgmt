package com.catalis.core.organization.core.mappers;

import com.catalis.core.organization.interfaces.dtos.BranchHoursDTO;
import com.catalis.core.organization.models.entities.BranchHours;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between BranchHours entity and BranchHoursDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BranchHoursMapper {

    /**
     * Converts a BranchHours entity to a BranchHoursDTO.
     *
     * @param entity the BranchHours entity to convert
     * @return the corresponding BranchHoursDTO
     */
    BranchHoursDTO toDTO(BranchHours entity);

    /**
     * Converts a BranchHoursDTO to a BranchHours entity.
     *
     * @param dto the BranchHoursDTO to convert
     * @return the corresponding BranchHours entity
     */
    BranchHours toEntity(BranchHoursDTO dto);
}