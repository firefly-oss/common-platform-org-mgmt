package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.BranchDepartmentMapper;
import com.catalis.core.organization.interfaces.dtos.BranchDepartmentDTO;
import com.catalis.core.organization.models.entities.BranchDepartment;
import com.catalis.core.organization.models.repositories.BranchDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class BranchDepartmentServiceImpl implements BranchDepartmentService {

    @Autowired
    private BranchDepartmentRepository repository;

    @Autowired
    private BranchDepartmentMapper mapper;

    @Override
    public Mono<PaginationResponse<BranchDepartmentDTO>> filterBranchDepartments(FilterRequest<BranchDepartmentDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        BranchDepartment.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<BranchDepartmentDTO> createBranchDepartment(BranchDepartmentDTO branchDepartmentDTO) {
        return Mono.just(branchDepartmentDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchDepartmentDTO> updateBranchDepartment(Long branchDepartmentId, BranchDepartmentDTO branchDepartmentDTO) {
        return repository.findById(branchDepartmentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch department not found with ID: " + branchDepartmentId)))
                .flatMap(existingBranchDepartment -> {
                    BranchDepartment updatedBranchDepartment = mapper.toEntity(branchDepartmentDTO);
                    updatedBranchDepartment.setId(branchDepartmentId);
                    return repository.save(updatedBranchDepartment);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBranchDepartment(Long branchDepartmentId) {
        return repository.findById(branchDepartmentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch department not found with ID: " + branchDepartmentId)))
                .flatMap(branchDepartment -> repository.deleteById(branchDepartmentId));
    }

    @Override
    public Mono<BranchDepartmentDTO> getBranchDepartmentById(Long branchDepartmentId) {
        return repository.findById(branchDepartmentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch department not found with ID: " + branchDepartmentId)))
                .map(mapper::toDTO);
    }
}