package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.interfaces.dtos.WorkingCalendarDTO;

import reactor.core.publisher.Mono;

/**
 * Service interface for managing working calendars.
 */
public interface WorkingCalendarService {
    /**
     * Filters the working calendars based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for WorkingCalendarDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of working calendars
     */
    Mono<PaginationResponse<WorkingCalendarDTO>> filterWorkingCalendars(FilterRequest<WorkingCalendarDTO> filterRequest);

    /**
     * Filters the working calendars for a specific bank based on the given criteria.
     *
     * @param bankId the unique identifier of the bank
     * @param filterRequest the request object containing filtering criteria for WorkingCalendarDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of working calendars
     */
    Mono<PaginationResponse<WorkingCalendarDTO>> filterWorkingCalendarsForBank(Long bankId, FilterRequest<WorkingCalendarDTO> filterRequest);

    /**
     * Creates a new working calendar based on the provided information.
     *
     * @param workingCalendarDTO the DTO object containing details of the working calendar to be created
     * @return a Mono that emits the created WorkingCalendarDTO object
     */
    Mono<WorkingCalendarDTO> createWorkingCalendar(WorkingCalendarDTO workingCalendarDTO);

    /**
     * Creates a new working calendar for a specific bank based on the provided information.
     *
     * @param bankId the unique identifier of the bank
     * @param workingCalendarDTO the DTO object containing details of the working calendar to be created
     * @return a Mono that emits the created WorkingCalendarDTO object
     */
    Mono<WorkingCalendarDTO> createWorkingCalendarForBank(Long bankId, WorkingCalendarDTO workingCalendarDTO);

    /**
     * Updates an existing working calendar with updated information.
     *
     * @param workingCalendarId the unique identifier of the working calendar to be updated
     * @param workingCalendarDTO the data transfer object containing the updated details of the working calendar
     * @return a reactive Mono containing the updated WorkingCalendarDTO
     */
    Mono<WorkingCalendarDTO> updateWorkingCalendar(Long workingCalendarId, WorkingCalendarDTO workingCalendarDTO);

    /**
     * Updates an existing working calendar for a specific bank with updated information.
     *
     * @param bankId the unique identifier of the bank
     * @param calendarId the unique identifier of the calendar to be updated
     * @param workingCalendarDTO the data transfer object containing the updated details of the working calendar
     * @return a reactive Mono containing the updated WorkingCalendarDTO
     */
    Mono<WorkingCalendarDTO> updateWorkingCalendarForBank(Long bankId, Long calendarId, WorkingCalendarDTO workingCalendarDTO);

    /**
     * Deletes a working calendar identified by its unique ID.
     *
     * @param workingCalendarId the unique identifier of the working calendar to be deleted
     * @return a Mono that completes when the working calendar is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteWorkingCalendar(Long workingCalendarId);

    /**
     * Deletes a working calendar for a specific bank identified by its unique ID.
     *
     * @param bankId the unique identifier of the bank
     * @param calendarId the unique identifier of the calendar to be deleted
     * @return a Mono that completes when the working calendar is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteWorkingCalendarForBank(Long bankId, Long calendarId);

    /**
     * Retrieves a working calendar by its unique identifier.
     *
     * @param workingCalendarId the unique identifier of the working calendar to retrieve
     * @return a Mono emitting the {@link WorkingCalendarDTO} representing the working calendar if found,
     *         or an empty Mono if the working calendar does not exist
     */
    Mono<WorkingCalendarDTO> getWorkingCalendarById(Long workingCalendarId);

    /**
     * Retrieves a working calendar for a specific bank by its unique identifier.
     *
     * @param bankId the unique identifier of the bank
     * @param calendarId the unique identifier of the calendar to retrieve
     * @return a Mono emitting the {@link WorkingCalendarDTO} representing the working calendar if found,
     *         or an empty Mono if the working calendar does not exist or doesn't belong to the specified bank
     */
    Mono<WorkingCalendarDTO> getWorkingCalendarByIdForBank(Long bankId, Long calendarId);
}
