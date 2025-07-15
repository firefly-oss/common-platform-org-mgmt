package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.BranchPositionMapper;
import com.catalis.core.organization.interfaces.dtos.BranchPositionDTO;
import com.catalis.core.organization.models.entities.BranchPosition;
import com.catalis.core.organization.models.repositories.BranchPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class BranchPositionServiceImpl implements BranchPositionService {

    @Autowired
    private BranchPositionRepository repository;

    @Autowired
    private BranchPositionMapper mapper;

    @Override
    public Mono<PaginationResponse<BranchPositionDTO>> filterBranchPositions(FilterRequest<BranchPositionDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        BranchPosition.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<BranchPositionDTO> createBranchPosition(BranchPositionDTO branchPositionDTO) {
        return Mono.just(branchPositionDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchPositionDTO> updateBranchPosition(Long branchPositionId, BranchPositionDTO branchPositionDTO) {
        return repository.findById(branchPositionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch position not found with ID: " + branchPositionId)))
                .flatMap(existingBranchPosition -> {
                    BranchPosition updatedBranchPosition = mapper.toEntity(branchPositionDTO);
                    updatedBranchPosition.setId(branchPositionId);
                    return repository.save(updatedBranchPosition);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBranchPosition(Long branchPositionId) {
        return repository.findById(branchPositionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch position not found with ID: " + branchPositionId)))
                .flatMap(branchPosition -> repository.deleteById(branchPositionId));
    }

    @Override
    public Mono<BranchPositionDTO> getBranchPositionById(Long branchPositionId) {
        return repository.findById(branchPositionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch position not found with ID: " + branchPositionId)))
                .map(mapper::toDTO);
    }
}