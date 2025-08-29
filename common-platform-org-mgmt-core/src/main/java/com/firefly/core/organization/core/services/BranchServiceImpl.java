package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BranchMapper;
import com.firefly.core.organization.interfaces.dtos.BranchDTO;
import com.firefly.core.organization.models.entities.Branch;
import com.firefly.core.organization.models.repositories.BranchRepository;
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

    @Autowired
    private BankService bankService;

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
    public Mono<PaginationResponse<BranchDTO>> filterBranchesForBank(Long bankId, FilterRequest<BranchDTO> filterRequest) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> filterBranches(filterRequest));
    }

    @Override
    public Mono<BranchDTO> createBranch(BranchDTO branchDTO) {
        return Mono.just(branchDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchDTO> createBranchForBank(Long bankId, BranchDTO branchDTO) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> {
                    branchDTO.setBankId(bankId);
                    return createBranch(branchDTO);
                });
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
    public Mono<BranchDTO> updateBranchForBank(Long bankId, Long branchId, BranchDTO branchDTO) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> getBranchById(branchId))
                .filter(branch -> branch.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found for bank with ID: " + bankId)))
                .flatMap(branch -> {
                    branchDTO.setBankId(bankId);
                    return updateBranch(branchId, branchDTO);
                });
    }

    @Override
    public Mono<Void> deleteBranch(Long branchId) {
        return repository.findById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .flatMap(branch -> repository.deleteById(branchId));
    }

    @Override
    public Mono<Void> deleteBranchForBank(Long bankId, Long branchId) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> getBranchById(branchId))
                .filter(branch -> branch.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found for bank with ID: " + bankId)))
                .flatMap(branch -> deleteBranch(branchId));
    }

    @Override
    public Mono<BranchDTO> getBranchById(Long branchId) {
        return repository.findById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found with ID: " + branchId)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BranchDTO> getBranchByIdForBank(Long bankId, Long branchId) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> getBranchById(branchId))
                .filter(branch -> branch.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Branch not found for bank with ID: " + bankId)));
    }
}
