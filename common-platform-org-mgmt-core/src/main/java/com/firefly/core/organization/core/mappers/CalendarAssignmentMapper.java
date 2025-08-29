package com.firefly.core.organization.core.mappers;

import com.firefly.core.organization.interfaces.dtos.CalendarAssignmentDTO;
import com.firefly.core.organization.models.entities.CalendarAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between CalendarAssignment entity and CalendarAssignmentDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CalendarAssignmentMapper {

    /**
     * Converts a CalendarAssignment entity to a CalendarAssignmentDTO.
     *
     * @param entity the CalendarAssignment entity to convert
     * @return the corresponding CalendarAssignmentDTO
     */
    CalendarAssignmentDTO toDTO(CalendarAssignment entity);

    /**
     * Converts a CalendarAssignmentDTO to a CalendarAssignment entity.
     *
     * @param dto the CalendarAssignmentDTO to convert
     * @return the corresponding CalendarAssignment entity
     */
    CalendarAssignment toEntity(CalendarAssignmentDTO dto);
}