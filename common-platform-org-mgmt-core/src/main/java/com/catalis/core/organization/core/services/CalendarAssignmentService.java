package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.interfaces.dtos.CalendarAssignmentDTO;

import reactor.core.publisher.Mono;

/**
 * Service interface for managing calendar assignments.
 */
public interface CalendarAssignmentService {
    /**
     * Filters the calendar assignments based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for CalendarAssignmentDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of calendar assignments
     */
    Mono<PaginationResponse<CalendarAssignmentDTO>> filterCalendarAssignments(FilterRequest<CalendarAssignmentDTO> filterRequest);
    
    /**
     * Creates a new calendar assignment based on the provided information.
     *
     * @param calendarAssignmentDTO the DTO object containing details of the calendar assignment to be created
     * @return a Mono that emits the created CalendarAssignmentDTO object
     */
    Mono<CalendarAssignmentDTO> createCalendarAssignment(CalendarAssignmentDTO calendarAssignmentDTO);
    
    /**
     * Updates an existing calendar assignment with updated information.
     *
     * @param calendarAssignmentId the unique identifier of the calendar assignment to be updated
     * @param calendarAssignmentDTO the data transfer object containing the updated details of the calendar assignment
     * @return a reactive Mono containing the updated CalendarAssignmentDTO
     */
    Mono<CalendarAssignmentDTO> updateCalendarAssignment(Long calendarAssignmentId, CalendarAssignmentDTO calendarAssignmentDTO);
    
    /**
     * Deletes a calendar assignment identified by its unique ID.
     *
     * @param calendarAssignmentId the unique identifier of the calendar assignment to be deleted
     * @return a Mono that completes when the calendar assignment is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteCalendarAssignment(Long calendarAssignmentId);
    
    /**
     * Retrieves a calendar assignment by its unique identifier.
     *
     * @param calendarAssignmentId the unique identifier of the calendar assignment to retrieve
     * @return a Mono emitting the {@link CalendarAssignmentDTO} representing the calendar assignment if found,
     *         or an empty Mono if the calendar assignment does not exist
     */
    Mono<CalendarAssignmentDTO> getCalendarAssignmentById(Long calendarAssignmentId);
}