package com.firefly.core.organization.core.mappers;

import com.firefly.core.organization.interfaces.dtos.BranchDepartmentDTO;
import com.firefly.core.organization.models.entities.BranchDepartment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between BranchDepartment entity and BranchDepartmentDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BranchDepartmentMapper {

    /**
     * Converts a BranchDepartment entity to a BranchDepartmentDTO.
     *
     * @param entity the BranchDepartment entity to convert
     * @return the corresponding BranchDepartmentDTO
     */
    BranchDepartmentDTO toDTO(BranchDepartment entity);

    /**
     * Converts a BranchDepartmentDTO to a BranchDepartment entity.
     *
     * @param dto the BranchDepartmentDTO to convert
     * @return the corresponding BranchDepartment entity
     */
    BranchDepartment toEntity(BranchDepartmentDTO dto);
}