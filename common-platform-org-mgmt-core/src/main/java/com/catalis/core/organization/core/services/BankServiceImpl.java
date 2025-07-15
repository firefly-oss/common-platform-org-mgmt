package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.BankMapper;
import com.catalis.core.organization.interfaces.dtos.BankDTO;
import com.catalis.core.organization.models.entities.Bank;
import com.catalis.core.organization.models.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository repository;

    @Autowired
    private BankMapper mapper;

    @Override
    public Mono<PaginationResponse<BankDTO>> filterBanks(FilterRequest<BankDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Bank.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<BankDTO> createBank(BankDTO bankDTO) {
        return Mono.just(bankDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BankDTO> updateBank(Long bankId, BankDTO bankDTO) {
        return repository.findById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(existingBank -> {
                    Bank updatedBank = mapper.toEntity(bankDTO);
                    updatedBank.setId(bankId);
                    return repository.save(updatedBank);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBank(Long bankId) {
        return repository.findById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> repository.deleteById(bankId));
    }

    @Override
    public Mono<BankDTO> getBankById(Long bankId) {
        return repository.findById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .map(mapper::toDTO);
    }
}