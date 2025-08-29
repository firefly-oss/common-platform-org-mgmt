package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BankDivisionMapper;
import com.firefly.core.organization.interfaces.dtos.BankDivisionDTO;
import com.firefly.core.organization.models.entities.BankDivision;
import com.firefly.core.organization.models.repositories.BankDivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class BankDivisionServiceImpl implements BankDivisionService {

    @Autowired
    private BankDivisionRepository repository;

    @Autowired
    private BankDivisionMapper mapper;

    @Autowired
    private BankService bankService;

    @Override
    public Mono<PaginationResponse<BankDivisionDTO>> filterBankDivisions(FilterRequest<BankDivisionDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        BankDivision.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<BankDivisionDTO> createBankDivision(BankDivisionDTO bankDivisionDTO) {
        return Mono.just(bankDivisionDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BankDivisionDTO> updateBankDivision(Long bankDivisionId, BankDivisionDTO bankDivisionDTO) {
        return repository.findById(bankDivisionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank division not found with ID: " + bankDivisionId)))
                .flatMap(existingBankDivision -> {
                    BankDivision updatedBankDivision = mapper.toEntity(bankDivisionDTO);
                    updatedBankDivision.setId(bankDivisionId);
                    return repository.save(updatedBankDivision);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBankDivision(Long bankDivisionId) {
        return repository.findById(bankDivisionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank division not found with ID: " + bankDivisionId)))
                .flatMap(bankDivision -> repository.deleteById(bankDivisionId));
    }

    @Override
    public Mono<BankDivisionDTO> getBankDivisionById(Long bankDivisionId) {
        return repository.findById(bankDivisionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank division not found with ID: " + bankDivisionId)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BankDivisionDTO> getBankDivisionByIdForBank(Long bankId, Long divisionId) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> getBankDivisionById(divisionId))
                .filter(division -> division.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Division not found for bank with ID: " + bankId)));
    }

    @Override
    public Mono<BankDivisionDTO> updateBankDivisionForBank(Long bankId, Long divisionId, BankDivisionDTO bankDivisionDTO) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> getBankDivisionById(divisionId))
                .filter(division -> division.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Division not found for bank with ID: " + bankId)))
                .flatMap(division -> {
                    bankDivisionDTO.setBankId(bankId);
                    return updateBankDivision(divisionId, bankDivisionDTO);
                });
    }

    @Override
    public Mono<Void> deleteBankDivisionForBank(Long bankId, Long divisionId) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> getBankDivisionById(divisionId))
                .filter(division -> division.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Division not found for bank with ID: " + bankId)))
                .flatMap(division -> deleteBankDivision(divisionId));
    }
}
