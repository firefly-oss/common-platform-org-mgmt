package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BranchDepartmentMapper;
import com.firefly.core.organization.interfaces.dtos.BranchDepartmentDTO;
import com.firefly.core.organization.models.entities.BranchDepartment;
import com.firefly.core.organization.models.repositories.BranchDepartmentRepository;
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

    @Autowired
    private BranchService branchService;

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
    public Mono<PaginationResponse<BranchDepartmentDTO>> filterBranchDepartmentsForBranch(Long branchId, FilterRequest<BranchDepartmentDTO> filterRequest) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> filterBranchDepartments(filterRequest));
    }

    @Override
    public Mono<BranchDepartmentDTO> createBranchDepartment(BranchDepartmentDTO branchDepartmentDTO) {
        return Mono.just(branchDepartmentDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchDepartmentDTO> createBranchDepartmentForBranch(Long branchId, BranchDepartmentDTO branchDepartmentDTO) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> {
                    branchDepartmentDTO.setBranchId(branchId);
                    return createBranchDepartment(branchDepartmentDTO);
                });
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
    public Mono<BranchDepartmentDTO> updateBranchDepartmentForBranch(Long branchId, Long departmentId, BranchDepartmentDTO branchDepartmentDTO) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> getBranchDepartmentById(departmentId))
                .filter(department -> department.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Department not found for branch with ID: " + branchId)))
                .flatMap(department -> {
                    branchDepartmentDTO.setBranchId(branchId);
                    return updateBranchDepartment(departmentId, branchDepartmentDTO);
                });
    }

    @Override
    public Mono<Void> deleteBranchDepartment(Long branchDepartmentId) {
        return repository.findById(branchDepartmentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch department not found with ID: " + branchDepartmentId)))
                .flatMap(branchDepartment -> repository.deleteById(branchDepartmentId));
    }

    @Override
    public Mono<Void> deleteBranchDepartmentForBranch(Long branchId, Long departmentId) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> getBranchDepartmentById(departmentId))
                .filter(department -> department.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Department not found for branch with ID: " + branchId)))
                .flatMap(department -> deleteBranchDepartment(departmentId));
    }

    @Override
    public Mono<BranchDepartmentDTO> getBranchDepartmentById(Long branchDepartmentId) {
        return repository.findById(branchDepartmentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch department not found with ID: " + branchDepartmentId)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchDepartmentDTO> getBranchDepartmentByIdForBranch(Long branchId, Long departmentId) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> getBranchDepartmentById(departmentId))
                .filter(department -> department.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Department not found for branch with ID: " + branchId)));
    }
}
