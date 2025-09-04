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

import com.firefly.core.organization.interfaces.dtos.BranchAuditLogDTO;
import com.firefly.core.organization.models.entities.BranchAuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between BranchAuditLog entity and BranchAuditLogDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BranchAuditLogMapper {

    /**
     * Converts a BranchAuditLog entity to a BranchAuditLogDTO.
     *
     * @param entity the BranchAuditLog entity to convert
     * @return the corresponding BranchAuditLogDTO
     */
    BranchAuditLogDTO toDTO(BranchAuditLog entity);

    /**
     * Converts a BranchAuditLogDTO to a BranchAuditLog entity.
     *
     * @param dto the BranchAuditLogDTO to convert
     * @return the corresponding BranchAuditLog entity
     */
    BranchAuditLog toEntity(BranchAuditLogDTO dto);
}