/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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