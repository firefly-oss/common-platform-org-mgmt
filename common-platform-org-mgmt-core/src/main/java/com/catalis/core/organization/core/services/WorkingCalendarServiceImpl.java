package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.WorkingCalendarMapper;
import com.catalis.core.organization.interfaces.dtos.WorkingCalendarDTO;
import com.catalis.core.organization.models.entities.WorkingCalendar;
import com.catalis.core.organization.models.repositories.WorkingCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class WorkingCalendarServiceImpl implements WorkingCalendarService {

    @Autowired
    private WorkingCalendarRepository repository;

    @Autowired
    private WorkingCalendarMapper mapper;

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
    public Mono<WorkingCalendarDTO> createWorkingCalendar(WorkingCalendarDTO workingCalendarDTO) {
        return Mono.just(workingCalendarDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<WorkingCalendarDTO> updateWorkingCalendar(Long workingCalendarId, WorkingCalendarDTO workingCalendarDTO) {
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
    public Mono<Void> deleteWorkingCalendar(Long workingCalendarId) {
        return repository.findById(workingCalendarId)
                .switchIfEmpty(Mono.error(new RuntimeException("Working calendar not found with ID: " + workingCalendarId)))
                .flatMap(workingCalendar -> repository.deleteById(workingCalendarId));
    }

    @Override
    public Mono<WorkingCalendarDTO> getWorkingCalendarById(Long workingCalendarId) {
        return repository.findById(workingCalendarId)
                .switchIfEmpty(Mono.error(new RuntimeException("Working calendar not found with ID: " + workingCalendarId)))
                .map(mapper::toDTO);
    }
}