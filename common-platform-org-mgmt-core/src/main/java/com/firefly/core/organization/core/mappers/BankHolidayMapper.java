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