package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BranchPositionMapper;
import com.firefly.core.organization.interfaces.dtos.BranchPositionDTO;
import com.firefly.core.organization.models.entities.BranchPosition;
import com.firefly.core.organization.models.repositories.BranchPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class BranchPositionServiceImpl implements BranchPositionService {

    @Autowired
    private BranchPositionRepository repository;

    @Autowired
    private BranchPositionMapper mapper;

    @Autowired
    private BranchService branchService;

    @Autowired
    private BranchDepartmentService branchDepartmentService;

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
    public Mono<PaginationResponse<BranchPositionDTO>> filterBranchPositionsForDepartment(UUID branchId, UUID departmentId, FilterRequest<BranchPositionDTO> filterRequest) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> branchDepartmentService.getBranchDepartmentById(departmentId))
                .filter(department -> department.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Department not found for branch with ID: " + branchId)))
                .flatMap(department -> filterBranchPositions(filterRequest));
    }

    @Override
    public Mono<BranchPositionDTO> createBranchPosition(BranchPositionDTO branchPositionDTO) {
        return Mono.just(branchPositionDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchPositionDTO> createBranchPositionForDepartment(UUID branchId, UUID departmentId, BranchPositionDTO branchPositionDTO) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> branchDepartmentService.getBranchDepartmentById(departmentId))
                .filter(department -> department.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Department not found for branch with ID: " + branchId)))
                .flatMap(department -> {
                    branchPositionDTO.setDepartmentId(departmentId);
                    return createBranchPosition(branchPositionDTO);
                });
    }

    @Override
    public Mono<BranchPositionDTO> updateBranchPosition(UUID branchPositionId, BranchPositionDTO branchPositionDTO) {
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
    public Mono<BranchPositionDTO> updateBranchPositionForDepartment(UUID branchId, UUID departmentId, UUID positionId, BranchPositionDTO branchPositionDTO) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> branchDepartmentService.getBranchDepartmentById(departmentId))
                .filter(department -> department.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Department not found for branch with ID: " + branchId)))
                .flatMap(department -> getBranchPositionById(positionId))
                .filter(position -> position.getDepartmentId().equals(departmentId))
                .switchIfEmpty(Mono.error(new RuntimeException("Position not found for department with ID: " + departmentId)))
                .flatMap(position -> {
                    branchPositionDTO.setDepartmentId(departmentId);
                    return updateBranchPosition(positionId, branchPositionDTO);
                });
    }

    @Override
    public Mono<Void> deleteBranchPosition(UUID branchPositionId) {
        return repository.findById(branchPositionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch position not found with ID: " + branchPositionId)))
                .flatMap(branchPosition -> repository.deleteById(branchPositionId));
    }

    @Override
    public Mono<Void> deleteBranchPositionForDepartment(UUID branchId, UUID departmentId, UUID positionId) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> branchDepartmentService.getBranchDepartmentById(departmentId))
                .filter(department -> department.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Department not found for branch with ID: " + branchId)))
                .flatMap(department -> getBranchPositionById(positionId))
                .filter(position -> position.getDepartmentId().equals(departmentId))
                .switchIfEmpty(Mono.error(new RuntimeException("Position not found for department with ID: " + departmentId)))
                .flatMap(position -> deleteBranchPosition(positionId));
    }

    @Override
    public Mono<BranchPositionDTO> getBranchPositionById(UUID branchPositionId) {
        return repository.findById(branchPositionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch position not found with ID: " + branchPositionId)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchPositionDTO> getBranchPositionByIdForDepartment(UUID branchId, UUID departmentId, UUID positionId) {
        return branchService.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> branchDepartmentService.getBranchDepartmentById(departmentId))
                .filter(department -> department.getBranchId().equals(branchId))
                .switchIfEmpty(Mono.error(new RuntimeException("Department not found for branch with ID: " + branchId)))
                .flatMap(department -> getBranchPositionById(positionId))
                .filter(position -> position.getDepartmentId().equals(departmentId))
                .switchIfEmpty(Mono.error(new RuntimeException("Position not found for department with ID: " + departmentId)));
    }
}
