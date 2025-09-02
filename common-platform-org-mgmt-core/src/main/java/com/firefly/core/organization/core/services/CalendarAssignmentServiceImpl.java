package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.CalendarAssignmentMapper;
import com.firefly.core.organization.interfaces.dtos.CalendarAssignmentDTO;
import com.firefly.core.organization.models.entities.CalendarAssignment;
import com.firefly.core.organization.models.repositories.CalendarAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class CalendarAssignmentServiceImpl implements CalendarAssignmentService {

    @Autowired
    private CalendarAssignmentRepository repository;

    @Autowired
    private CalendarAssignmentMapper mapper;

    @Autowired
    private BankService bankService;

    @Autowired
    private WorkingCalendarService workingCalendarService;

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
    public Mono<PaginationResponse<CalendarAssignmentDTO>> filterCalendarAssignmentsForCalendar(UUID bankId, UUID calendarId, FilterRequest<CalendarAssignmentDTO> filterRequest) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> workingCalendarService.getWorkingCalendarById(calendarId))
                .filter(calendar -> calendar.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar not found for bank with ID: " + bankId)))
                .flatMap(calendar -> filterCalendarAssignments(filterRequest));
    }

    @Override
    public Mono<CalendarAssignmentDTO> createCalendarAssignment(CalendarAssignmentDTO calendarAssignmentDTO) {
        return Mono.just(calendarAssignmentDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CalendarAssignmentDTO> createCalendarAssignmentForCalendar(UUID bankId, UUID calendarId, CalendarAssignmentDTO calendarAssignmentDTO) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> workingCalendarService.getWorkingCalendarById(calendarId))
                .filter(calendar -> calendar.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar not found for bank with ID: " + bankId)))
                .flatMap(calendar -> {
                    calendarAssignmentDTO.setCalendarId(calendarId);
                    return createCalendarAssignment(calendarAssignmentDTO);
                });
    }

    @Override
    public Mono<CalendarAssignmentDTO> updateCalendarAssignment(UUID calendarAssignmentId, CalendarAssignmentDTO calendarAssignmentDTO) {
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
    public Mono<CalendarAssignmentDTO> updateCalendarAssignmentForCalendar(UUID bankId, UUID calendarId, UUID assignmentId, CalendarAssignmentDTO calendarAssignmentDTO) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> workingCalendarService.getWorkingCalendarById(calendarId))
                .filter(calendar -> calendar.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar not found for bank with ID: " + bankId)))
                .flatMap(calendar -> getCalendarAssignmentById(assignmentId))
                .filter(assignment -> assignment.getCalendarId().equals(calendarId))
                .switchIfEmpty(Mono.error(new RuntimeException("Assignment not found for calendar with ID: " + calendarId)))
                .flatMap(assignment -> {
                    calendarAssignmentDTO.setCalendarId(calendarId);
                    return updateCalendarAssignment(assignmentId, calendarAssignmentDTO);
                });
    }

    @Override
    public Mono<Void> deleteCalendarAssignment(UUID calendarAssignmentId) {
        return repository.findById(calendarAssignmentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar assignment not found with ID: " + calendarAssignmentId)))
                .flatMap(calendarAssignment -> repository.deleteById(calendarAssignmentId));
    }

    @Override
    public Mono<Void> deleteCalendarAssignmentForCalendar(UUID bankId, UUID calendarId, UUID assignmentId) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> workingCalendarService.getWorkingCalendarById(calendarId))
                .filter(calendar -> calendar.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar not found for bank with ID: " + bankId)))
                .flatMap(calendar -> getCalendarAssignmentById(assignmentId))
                .filter(assignment -> assignment.getCalendarId().equals(calendarId))
                .switchIfEmpty(Mono.error(new RuntimeException("Assignment not found for calendar with ID: " + calendarId)))
                .flatMap(assignment -> deleteCalendarAssignment(assignmentId));
    }

    @Override
    public Mono<CalendarAssignmentDTO> getCalendarAssignmentById(UUID calendarAssignmentId) {
        return repository.findById(calendarAssignmentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar assignment not found with ID: " + calendarAssignmentId)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CalendarAssignmentDTO> getCalendarAssignmentByIdForCalendar(UUID bankId, UUID calendarId, UUID assignmentId) {
        return bankService.getBankById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found with ID: " + bankId)))
                .flatMap(bank -> workingCalendarService.getWorkingCalendarById(calendarId))
                .filter(calendar -> calendar.getBankId().equals(bankId))
                .switchIfEmpty(Mono.error(new RuntimeException("Calendar not found for bank with ID: " + bankId)))
                .flatMap(calendar -> getCalendarAssignmentById(assignmentId))
                .filter(assignment -> assignment.getCalendarId().equals(calendarId))
                .switchIfEmpty(Mono.error(new RuntimeException("Assignment not found for calendar with ID: " + calendarId)));
    }
}
