package com.catalis.core.organization.core.mappers;

import com.catalis.core.organization.interfaces.dtos.BranchDTO;
import com.catalis.core.organization.models.entities.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between Branch entity and BranchDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BranchMapper {

    /**
     * Converts a Branch entity to a BranchDTO.
     *
     * @param entity the Branch entity to convert
     * @return the corresponding BranchDTO
     */
    BranchDTO toDTO(Branch entity);

    /**
     * Converts a BranchDTO to a Branch entity.
     *
     * @param dto the BranchDTO to convert
     * @return the corresponding Branch entity
     */
    Branch toEntity(BranchDTO dto);
}