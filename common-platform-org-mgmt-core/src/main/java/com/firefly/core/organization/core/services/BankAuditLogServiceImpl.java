package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BankAuditLogMapper;
import com.firefly.core.organization.interfaces.dtos.BankAuditLogDTO;
import com.firefly.core.organization.models.entities.BankAuditLog;
import com.firefly.core.organization.models.repositories.BankAuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class BankAuditLogServiceImpl implements BankAuditLogService {

    @Autowired
    private BankAuditLogRepository repository;

    @Autowired
    private BankAuditLogMapper mapper;

    @Override
    public Mono<PaginationResponse<BankAuditLogDTO>> filterBankAuditLogs(FilterRequest<BankAuditLogDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        BankAuditLog.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<BankAuditLogDTO> createBankAuditLog(BankAuditLogDTO bankAuditLogDTO) {
        return Mono.just(bankAuditLogDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BankAuditLogDTO> updateBankAuditLog(Long bankAuditLogId, BankAuditLogDTO bankAuditLogDTO) {
        return repository.findById(bankAuditLogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank audit log not found with ID: " + bankAuditLogId)))
                .flatMap(existingBankAuditLog -> {
                    BankAuditLog updatedBankAuditLog = mapper.toEntity(bankAuditLogDTO);
                    updatedBankAuditLog.setId(bankAuditLogId);
                    return repository.save(updatedBankAuditLog);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBankAuditLog(Long bankAuditLogId) {
        return repository.findById(bankAuditLogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank audit log not found with ID: " + bankAuditLogId)))
                .flatMap(bankAuditLog -> repository.deleteById(bankAuditLogId));
    }

    @Override
    public Mono<BankAuditLogDTO> getBankAuditLogById(Long bankAuditLogId) {
        return repository.findById(bankAuditLogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank audit log not found with ID: " + bankAuditLogId)))
                .map(mapper::toDTO);
    }
}