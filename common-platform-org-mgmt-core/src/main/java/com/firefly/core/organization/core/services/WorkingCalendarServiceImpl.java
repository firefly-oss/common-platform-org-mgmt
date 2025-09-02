package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.WorkingCalendarMapper;
import com.firefly.core.organization.interfaces.dtos.WorkingCalendarDTO;
import com.firefly.core.organization.models.entities.WorkingCalendar;
import com.firefly.core.organization.models.repositories.WorkingCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class WorkingCalendarServiceImpl implements WorkingCalendarService {

    @Autowired
    private WorkingCalendarRepository repository;

    @Autowired
    private WorkingCalendarMapper mapper;

    @Autowired
    private BankService bankService;

    @Override
    public Mono<PaginationResponse<WorkingCalendarDTO>> filterWorkingCalendars(FilterRequest<WorkingCalendarDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        WorkingCalendar.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaginationResponse<WorkingCalendarDTO>> filterWorkingCalendarsForBank(UUID bankId, FilterRequest<WorkingCalendarDTO> filterRequest) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> filterWorkingCalendars(filterRequest));
    }

    @Override
    public Mono<WorkingCalendarDTO> createWorkingCalendar(WorkingCalendarDTO workingCalendarDTO) {
        return Mono.just(workingCalendarDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<WorkingCalendarDTO> createWorkingCalendarForBank(UUID bankId, WorkingCalendarDTO workingCalendarDTO) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> {
                    workingCalendarDTO.setBankId(bankId);
                    return createWorkingCalendar(workingCalendarDTO);
                });
    }

    @Override
    public Mono<WorkingCalendarDTO> updateWorkingCalendar(UUID workingCalendarId, WorkingCalendarDTO workingCalendarDTO) {
        return repository.findById(workingCalendarId)
                .switchIfEmpty(Mono.error(new RuntimeException("Working calendar not found with ID: " + workingCalendarId)))
                .flatMap(existingWorkingCalendar -> {
                    WorkingCalendar updatedWorkingCalendar = mapper.toEntity(workingCalendarDTO);
                    updatedWorkingCalendar.setId(workingCalendarId);
                    return repository.save(updatedWorkingCalendar);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<WorkingCalendarDTO> updateWorkingCalendarForBank(UUID bankId, UUID calendarId, WorkingCalendarDTO workingCalendarDTO) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> getWorkingCalendarById(calendarId))
                .filter(calendar -> calendar.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar not found for bank with ID: " + bankId)))
                .flatMap(calendar -> {
                    workingCalendarDTO.setBankId(bankId);
                    return updateWorkingCalendar(calendarId, workingCalendarDTO);
                });
    }

    @Override
    public Mono<Void> deleteWorkingCalendar(UUID workingCalendarId) {
        return repository.findById(workingCalendarId)
                .switchIfEmpty(Mono.error(new RuntimeException("Working calendar not found with ID: " + workingCalendarId)))
                .flatMap(workingCalendar -> repository.deleteById(workingCalendarId));
    }

    @Override
    public Mono<Void> deleteWorkingCalendarForBank(UUID bankId, UUID calendarId) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> getWorkingCalendarById(calendarId))
                .filter(calendar -> calendar.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar not found for bank with ID: " + bankId)))
                .flatMap(calendar -> deleteWorkingCalendar(calendarId));
    }

    @Override
    public Mono<WorkingCalendarDTO> getWorkingCalendarById(UUID workingCalendarId) {
        return repository.findById(workingCalendarId)
                .switchIfEmpty(Mono.error(new RuntimeException("Working calendar not found with ID: " + workingCalendarId)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<WorkingCalendarDTO> getWorkingCalendarByIdForBank(UUID bankId, UUID calendarId) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> getWorkingCalendarById(calendarId))
                .filter(calendar -> calendar.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar not found for bank with ID: " + bankId)));
    }
}
