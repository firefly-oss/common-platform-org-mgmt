package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.BranchHoursMapper;
import com.catalis.core.organization.interfaces.dtos.BranchHoursDTO;
import com.catalis.core.organization.models.entities.BranchHours;
import com.catalis.core.organization.models.repositories.BranchHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class BranchHoursServiceImpl implements BranchHoursService {

    @Autowired
    private BranchHoursRepository repository;

    @Autowired
    private BranchHoursMapper mapper;

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
    public Mono<BranchHoursDTO> createBranchHours(BranchHoursDTO branchHoursDTO) {
        return Mono.just(branchHoursDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchHoursDTO> updateBranchHours(Long branchHoursId, BranchHoursDTO branchHoursDTO) {
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
    public Mono<Void> deleteBranchHours(Long branchHoursId) {
        return repository.findById(branchHoursId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch hours not found with ID: " + branchHoursId)))
                .flatMap(branchHours -> repository.deleteById(branchHoursId));
    }

    @Override
    public Mono<BranchHoursDTO> getBranchHoursById(Long branchHoursId) {
        return repository.findById(branchHoursId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch hours not found with ID: " + branchHoursId)))
                .map(mapper::toDTO);
    }
}