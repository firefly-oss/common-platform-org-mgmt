package com.catalis.core.organization.core.mappers;

import com.catalis.core.organization.interfaces.dtos.WorkingCalendarDTO;
import com.catalis.core.organization.models.entities.WorkingCalendar;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between WorkingCalendar entity and WorkingCalendarDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkingCalendarMapper {

    /**
     * Converts a WorkingCalendar entity to a WorkingCalendarDTO.
     *
     * @param entity the WorkingCalendar entity to convert
     * @return the corresponding WorkingCalendarDTO
     */
    WorkingCalendarDTO toDTO(WorkingCalendar entity);

    /**
     * Converts a WorkingCalendarDTO to a WorkingCalendar entity.
     *
     * @param dto the WorkingCalendarDTO to convert
     * @return the corresponding WorkingCalendar entity
     */
    WorkingCalendar toEntity(WorkingCalendarDTO dto);
}