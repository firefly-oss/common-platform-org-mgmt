package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BankRegionMapper;
import com.firefly.core.organization.interfaces.dtos.BankRegionDTO;
import com.firefly.core.organization.models.entities.BankRegion;
import com.firefly.core.organization.models.repositories.BankRegionRepository;
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

    @Autowired
    private BankDivisionService bankDivisionService;

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
    public Mono<PaginationResponse<BankRegionDTO>> filterBankRegionsForDivision(Long bankId, Long divisionId, FilterRequest<BankRegionDTO> filterRequest) {
        return bankDivisionService.getBankDivisionById(divisionId)
                .filter(division -> division.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Division not found for bank with ID: " + bankId)))
                .flatMap(division -> filterBankRegions(filterRequest));
    }

    @Override
    public Mono<BankRegionDTO> createBankRegion(BankRegionDTO bankRegionDTO) {
        return Mono.just(bankRegionDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BankRegionDTO> createBankRegionForDivision(Long bankId, Long divisionId, BankRegionDTO bankRegionDTO) {
        return bankDivisionService.getBankDivisionById(divisionId)
                .filter(division -> division.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Division not found for bank with ID: " + bankId)))
                .flatMap(division -> {
                    bankRegionDTO.setDivisionId(divisionId);
                    return createBankRegion(bankRegionDTO);
                });
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
    public Mono<BankRegionDTO> updateBankRegionForDivision(Long bankId, Long divisionId, Long regionId, BankRegionDTO bankRegionDTO) {
        return bankDivisionService.getBankDivisionById(divisionId)
                .filter(division -> division.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Division not found for bank with ID: " + bankId)))
                .flatMap(division -> getBankRegionById(regionId))
                .filter(region -> region.getDivisionId().equals(divisionId))
                .switchIfEmpty(Mono.error(new RuntimeException("Region not found for division with ID: " + divisionId)))
                .flatMap(region -> {
                    bankRegionDTO.setDivisionId(divisionId);
                    return updateBankRegion(regionId, bankRegionDTO);
                });
    }

    @Override
    public Mono<Void> deleteBankRegion(Long bankRegionId) {
        return repository.findById(bankRegionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank region not found with ID: " + bankRegionId)))
                .flatMap(bankRegion -> repository.deleteById(bankRegionId));
    }

    @Override
    public Mono<Void> deleteBankRegionForDivision(Long bankId, Long divisionId, Long regionId) {
        return bankDivisionService.getBankDivisionById(divisionId)
                .filter(division -> division.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Division not found for bank with ID: " + bankId)))
                .flatMap(division -> getBankRegionById(regionId))
                .filter(region -> region.getDivisionId().equals(divisionId))
                .switchIfEmpty(Mono.error(new RuntimeException("Region not found for division with ID: " + divisionId)))
                .flatMap(region -> deleteBankRegion(regionId));
    }

    @Override
    public Mono<BankRegionDTO> getBankRegionById(Long bankRegionId) {
        return repository.findById(bankRegionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank region not found with ID: " + bankRegionId)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BankRegionDTO> getBankRegionByIdForDivision(Long bankId, Long divisionId, Long regionId) {
        return bankDivisionService.getBankDivisionById(divisionId)
                .filter(division -> division.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Division not found for bank with ID: " + bankId)))
                .flatMap(division -> getBankRegionById(regionId))
                .filter(region -> region.getDivisionId().equals(divisionId))
                .switchIfEmpty(Mono.error(new RuntimeException("Region not found for division with ID: " + divisionId)));
    }
}
