package com.catalis.core.organization.core.mappers;

import com.catalis.core.organization.interfaces.dtos.BranchPositionDTO;
import com.catalis.core.organization.models.entities.BranchPosition;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between BranchPosition entity and BranchPositionDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BranchPositionMapper {

    /**
     * Converts a BranchPosition entity to a BranchPositionDTO.
     *
     * @param entity the BranchPosition entity to convert
     * @return the corresponding BranchPositionDTO
     */
    BranchPositionDTO toDTO(BranchPosition entity);

    /**
     * Converts a BranchPositionDTO to a BranchPosition entity.
     *
     * @param dto the BranchPositionDTO to convert
     * @return the corresponding BranchPosition entity
     */
    BranchPosition toEntity(BranchPositionDTO dto);
}