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


package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BranchHoursMapper;
import com.firefly.core.organization.interfaces.dtos.BranchHoursDTO;
import com.firefly.core.organization.models.entities.BranchHours;
import com.firefly.core.organization.models.repositories.BranchHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class BranchHoursServiceImpl implements BranchHoursService {

    @Autowired
    private BranchHoursRepository repository;

    @Autowired
    private BranchHoursMapper mapper;

    @Autowired
    private BranchService branchService;

    @Override
    public Mono<PaginationResponse<BranchHoursDTO>> filterBranchHours(FilterRequest<BranchHoursDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        BranchHours.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaginationResponse<BranchHoursDTO>> filterBranchHoursForBranch(UUID branchId, FilterRequest<BranchHoursDTO> filterRequest) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> filterBranchHours(filterRequest));
    }

    @Override
    public Mono<BranchHoursDTO> createBranchHours(BranchHoursDTO branchHoursDTO) {
        return Mono.just(branchHoursDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchHoursDTO> createBranchHoursForBranch(UUID branchId, BranchHoursDTO branchHoursDTO) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> {
                    branchHoursDTO.setBranchId(branchId);
                    return createBranchHours(branchHoursDTO);
                });
    }

    @Override
    public Mono<BranchHoursDTO> updateBranchHours(UUID branchHoursId, BranchHoursDTO branchHoursDTO) {
        return repository.findById(branchHoursId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch hours not found with ID: " + branchHoursId)))
                .flatMap(existingBranchHours -> {
                    BranchHours updatedBranchHours = mapper.toEntity(branchHoursDTO);
                    updatedBranchHours.setId(branchHoursId);
                    return repository.save(updatedBranchHours);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchHoursDTO> updateBranchHoursForBranch(UUID branchId, UUID hoursId, BranchHoursDTO branchHoursDTO) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> getBranchHoursById(hoursId))
                .filter(hours -> hours.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Hours not found for branch with ID: " + branchId)))
                .flatMap(hours -> {
                    branchHoursDTO.setBranchId(branchId);
                    return updateBranchHours(hoursId, branchHoursDTO);
                });
    }

    @Override
    public Mono<Void> deleteBranchHours(UUID branchHoursId) {
        return repository.findById(branchHoursId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch hours not found with ID: " + branchHoursId)))
                .flatMap(branchHours -> repository.deleteById(branchHoursId));
    }

    @Override
    public Mono<Void> deleteBranchHoursForBranch(UUID branchId, UUID hoursId) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> getBranchHoursById(hoursId))
                .filter(hours -> hours.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Hours not found for branch with ID: " + branchId)))
                .flatMap(hours -> deleteBranchHours(hoursId));
    }

    @Override
    public Mono<BranchHoursDTO> getBranchHoursById(UUID branchHoursId) {
        return repository.findById(branchHoursId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch hours not found with ID: " + branchHoursId)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchHoursDTO> getBranchHoursByIdForBranch(UUID branchId, UUID hoursId) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> getBranchHoursById(hoursId))
                .filter(hours -> hours.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Hours not found for branch with ID: " + branchId)));
    }
}
