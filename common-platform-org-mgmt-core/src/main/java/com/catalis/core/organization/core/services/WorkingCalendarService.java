package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.interfaces.dtos.WorkingCalendarDTO;

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
     * Creates a new working calendar based on the provided information.
     *
     * @param workingCalendarDTO the DTO object containing details of the working calendar to be created
     * @return a Mono that emits the created WorkingCalendarDTO object
     */
    Mono<WorkingCalendarDTO> createWorkingCalendar(WorkingCalendarDTO workingCalendarDTO);
    
    /**
     * Updates an existing working calendar with updated information.
     *
     * @param workingCalendarId the unique identifier of the working calendar to be updated
     * @param workingCalendarDTO the data transfer object containing the updated details of the working calendar
     * @return a reactive Mono containing the updated WorkingCalendarDTO
     */
    Mono<WorkingCalendarDTO> updateWorkingCalendar(Long workingCalendarId, WorkingCalendarDTO workingCalendarDTO);
    
    /**
     * Deletes a working calendar identified by its unique ID.
     *
     * @param workingCalendarId the unique identifier of the working calendar to be deleted
     * @return a Mono that completes when the working calendar is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteWorkingCalendar(Long workingCalendarId);
    
    /**
     * Retrieves a working calendar by its unique identifier.
     *
     * @param workingCalendarId the unique identifier of the working calendar to retrieve
     * @return a Mono emitting the {@link WorkingCalendarDTO} representing the working calendar if found,
     *         or an empty Mono if the working calendar does not exist
     */
    Mono<WorkingCalendarDTO> getWorkingCalendarById(Long workingCalendarId);
}