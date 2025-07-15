package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.BankDivisionMapper;
import com.catalis.core.organization.interfaces.dtos.BankDivisionDTO;
import com.catalis.core.organization.models.entities.BankDivision;
import com.catalis.core.organization.models.repositories.BankDivisionRepository;
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
}