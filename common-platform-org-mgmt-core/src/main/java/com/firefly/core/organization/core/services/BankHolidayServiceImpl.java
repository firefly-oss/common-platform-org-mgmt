package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BankHolidayMapper;
import com.firefly.core.organization.interfaces.dtos.BankHolidayDTO;
import com.firefly.core.organization.models.entities.BankHoliday;
import com.firefly.core.organization.models.repositories.BankHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class BankHolidayServiceImpl implements BankHolidayService {

    @Autowired
    private BankHolidayRepository repository;

    @Autowired
    private BankHolidayMapper mapper;

    @Override
    public Mono<PaginationResponse<BankHolidayDTO>> filterBankHolidays(FilterRequest<BankHolidayDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        BankHoliday.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<BankHolidayDTO> createBankHoliday(BankHolidayDTO bankHolidayDTO) {
        return Mono.just(bankHolidayDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BankHolidayDTO> updateBankHoliday(UUID bankHolidayId, BankHolidayDTO bankHolidayDTO) {
        return repository.findById(bankHolidayId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank holiday not found with ID: " + bankHolidayId)))
                .flatMap(existingBankHoliday -> {
                    BankHoliday updatedBankHoliday = mapper.toEntity(bankHolidayDTO);
                    updatedBankHoliday.setId(bankHolidayId);
                    return repository.save(updatedBankHoliday);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBankHoliday(UUID bankHolidayId) {
        return repository.findById(bankHolidayId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank holiday not found with ID: " + bankHolidayId)))
                .flatMap(bankHoliday -> repository.deleteById(bankHolidayId));
    }

    @Override
    public Mono<BankHolidayDTO> getBankHolidayById(UUID bankHolidayId) {
        return repository.findById(bankHolidayId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank holiday not found with ID: " + bankHolidayId)))
                .map(mapper::toDTO);
    }
}