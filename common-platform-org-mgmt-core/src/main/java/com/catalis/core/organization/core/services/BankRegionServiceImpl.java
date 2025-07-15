package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.BankRegionMapper;
import com.catalis.core.organization.interfaces.dtos.BankRegionDTO;
import com.catalis.core.organization.models.entities.BankRegion;
import com.catalis.core.organization.models.repositories.BankRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class BankRegionServiceImpl implements BankRegionService {

    @Autowired
    private BankRegionRepository repository;

    @Autowired
    private BankRegionMapper mapper;

    @Override
    public Mono<PaginationResponse<BankRegionDTO>> filterBankRegions(FilterRequest<BankRegionDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        BankRegion.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<BankRegionDTO> createBankRegion(BankRegionDTO bankRegionDTO) {
        return Mono.just(bankRegionDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BankRegionDTO> updateBankRegion(Long bankRegionId, BankRegionDTO bankRegionDTO) {
        return repository.findById(bankRegionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank region not found with ID: " + bankRegionId)))
                .flatMap(existingBankRegion -> {
                    BankRegion updatedBankRegion = mapper.toEntity(bankRegionDTO);
                    updatedBankRegion.setId(bankRegionId);
                    return repository.save(updatedBankRegion);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBankRegion(Long bankRegionId) {
        return repository.findById(bankRegionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank region not found with ID: " + bankRegionId)))
                .flatMap(bankRegion -> repository.deleteById(bankRegionId));
    }

    @Override
    public Mono<BankRegionDTO> getBankRegionById(Long bankRegionId) {
        return repository.findById(bankRegionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank region not found with ID: " + bankRegionId)))
                .map(mapper::toDTO);
    }
}