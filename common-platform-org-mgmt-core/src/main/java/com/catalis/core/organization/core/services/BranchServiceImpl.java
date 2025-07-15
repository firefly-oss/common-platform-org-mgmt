package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.BranchMapper;
import com.catalis.core.organization.interfaces.dtos.BranchDTO;
import com.catalis.core.organization.models.entities.Branch;
import com.catalis.core.organization.models.repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository repository;

    @Autowired
    private BranchMapper mapper;

    @Override
    public Mono<PaginationResponse<BranchDTO>> filterBranches(FilterRequest<BranchDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Branch.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<BranchDTO> createBranch(BranchDTO branchDTO) {
        return Mono.just(branchDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchDTO> updateBranch(Long branchId, BranchDTO branchDTO) {
        return repository.findById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(existingBranch -> {
                    Branch updatedBranch = mapper.toEntity(branchDTO);
                    updatedBranch.setId(branchId);
                    return repository.save(updatedBranch);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBranch(Long branchId) {
        return repository.findById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> repository.deleteById(branchId));
    }

    @Override
    public Mono<BranchDTO> getBranchById(Long branchId) {
        return repository.findById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .map(mapper::toDTO);
    }
}