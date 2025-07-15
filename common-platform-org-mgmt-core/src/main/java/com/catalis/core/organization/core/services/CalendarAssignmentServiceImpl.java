package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.CalendarAssignmentMapper;
import com.catalis.core.organization.interfaces.dtos.CalendarAssignmentDTO;
import com.catalis.core.organization.models.entities.CalendarAssignment;
import com.catalis.core.organization.models.repositories.CalendarAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class CalendarAssignmentServiceImpl implements CalendarAssignmentService {

    @Autowired
    private CalendarAssignmentRepository repository;

    @Autowired
    private CalendarAssignmentMapper mapper;

    @Override
    public Mono<PaginationResponse<CalendarAssignmentDTO>> filterCalendarAssignments(FilterRequest<CalendarAssignmentDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        CalendarAssignment.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<CalendarAssignmentDTO> createCalendarAssignment(CalendarAssignmentDTO calendarAssignmentDTO) {
        return Mono.just(calendarAssignmentDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CalendarAssignmentDTO> updateCalendarAssignment(Long calendarAssignmentId, CalendarAssignmentDTO calendarAssignmentDTO) {
        return repository.findById(calendarAssignmentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar assignment not found with ID: " + calendarAssignmentId)))
                .flatMap(existingCalendarAssignment -> {
                    CalendarAssignment updatedCalendarAssignment = mapper.toEntity(calendarAssignmentDTO);
                    updatedCalendarAssignment.setId(calendarAssignmentId);
                    return repository.save(updatedCalendarAssignment);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCalendarAssignment(Long calendarAssignmentId) {
        return repository.findById(calendarAssignmentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar assignment not found with ID: " + calendarAssignmentId)))
                .flatMap(calendarAssignment -> repository.deleteById(calendarAssignmentId));
    }

    @Override
    public Mono<CalendarAssignmentDTO> getCalendarAssignmentById(Long calendarAssignmentId) {
        return repository.findById(calendarAssignmentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar assignment not found with ID: " + calendarAssignmentId)))
                .map(mapper::toDTO);
    }
}