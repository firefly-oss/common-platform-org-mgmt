package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BranchAuditLogMapper;
import com.firefly.core.organization.interfaces.dtos.BranchAuditLogDTO;
import com.firefly.core.organization.models.entities.BranchAuditLog;
import com.firefly.core.organization.models.repositories.BranchAuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class BranchAuditLogServiceImpl implements BranchAuditLogService {

    @Autowired
    private BranchAuditLogRepository repository;

    @Autowired
    private BranchAuditLogMapper mapper;

    @Override
    public Mono<PaginationResponse<BranchAuditLogDTO>> filterBranchAuditLogs(FilterRequest<BranchAuditLogDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        BranchAuditLog.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<BranchAuditLogDTO> createBranchAuditLog(BranchAuditLogDTO branchAuditLogDTO) {
        return Mono.just(branchAuditLogDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchAuditLogDTO> updateBranchAuditLog(UUID branchAuditLogId, BranchAuditLogDTO branchAuditLogDTO) {
        return repository.findById(branchAuditLogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch audit log not found with ID: " + branchAuditLogId)))
                .flatMap(existingBranchAuditLog -> {
                    BranchAuditLog updatedBranchAuditLog = mapper.toEntity(branchAuditLogDTO);
                    updatedBranchAuditLog.setId(branchAuditLogId);
                    return repository.save(updatedBranchAuditLog);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBranchAuditLog(UUID branchAuditLogId) {
        return repository.findById(branchAuditLogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch audit log not found with ID: " + branchAuditLogId)))
                .flatMap(branchAuditLog -> repository.deleteById(branchAuditLogId));
    }

    @Override
    public Mono<BranchAuditLogDTO> getBranchAuditLogById(UUID branchAuditLogId) {
        return repository.findById(branchAuditLogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch audit log not found with ID: " + branchAuditLogId)))
                .map(mapper::toDTO);
    }
}