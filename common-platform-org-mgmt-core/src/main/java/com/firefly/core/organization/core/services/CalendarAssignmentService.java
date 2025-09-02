package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.interfaces.dtos.CalendarAssignmentDTO;

import reactor.core.publisher.Mono;
import java.util.UUID;

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
     * Filters the calendar assignments for a specific working calendar based on the given criteria.
     *
     * @param bankId the unique identifier of the bank
     * @param calendarId the unique identifier of the calendar
     * @param filterRequest the request object containing filtering criteria for CalendarAssignmentDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of calendar assignments
     */
    Mono<PaginationResponse<CalendarAssignmentDTO>> filterCalendarAssignmentsForCalendar(UUID bankId, UUID calendarId, FilterRequest<CalendarAssignmentDTO> filterRequest);

    /**
     * Creates a new calendar assignment based on the provided information.
     *
     * @param calendarAssignmentDTO the DTO object containing details of the calendar assignment to be created
     * @return a Mono that emits the created CalendarAssignmentDTO object
     */
    Mono<CalendarAssignmentDTO> createCalendarAssignment(CalendarAssignmentDTO calendarAssignmentDTO);

    /**
     * Creates a new calendar assignment for a specific working calendar based on the provided information.
     *
     * @param bankId the unique identifier of the bank
     * @param calendarId the unique identifier of the calendar
     * @param calendarAssignmentDTO the DTO object containing details of the calendar assignment to be created
     * @return a Mono that emits the created CalendarAssignmentDTO object
     */
    Mono<CalendarAssignmentDTO> createCalendarAssignmentForCalendar(UUID bankId, UUID calendarId, CalendarAssignmentDTO calendarAssignmentDTO);

    /**
     * Updates an existing calendar assignment with updated information.
     *
     * @param calendarAssignmentId the unique identifier of the calendar assignment to be updated
     * @param calendarAssignmentDTO the data transfer object containing the updated details of the calendar assignment
     * @return a reactive Mono containing the updated CalendarAssignmentDTO
     */
    Mono<CalendarAssignmentDTO> updateCalendarAssignment(UUID calendarAssignmentId, CalendarAssignmentDTO calendarAssignmentDTO);

    /**
     * Updates an existing calendar assignment for a specific working calendar with updated information.
     *
     * @param bankId the unique identifier of the bank
     * @param calendarId the unique identifier of the calendar
     * @param assignmentId the unique identifier of the assignment to be updated
     * @param calendarAssignmentDTO the data transfer object containing the updated details of the calendar assignment
     * @return a reactive Mono containing the updated CalendarAssignmentDTO
     */
    Mono<CalendarAssignmentDTO> updateCalendarAssignmentForCalendar(UUID bankId, UUID calendarId, UUID assignmentId, CalendarAssignmentDTO calendarAssignmentDTO);

    /**
     * Deletes a calendar assignment identified by its unique ID.
     *
     * @param calendarAssignmentId the unique identifier of the calendar assignment to be deleted
     * @return a Mono that completes when the calendar assignment is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteCalendarAssignment(UUID calendarAssignmentId);

    /**
     * Deletes a calendar assignment for a specific working calendar identified by its unique ID.
     *
     * @param bankId the unique identifier of the bank
     * @param calendarId the unique identifier of the calendar
     * @param assignmentId the unique identifier of the assignment to be deleted
     * @return a Mono that completes when the calendar assignment is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteCalendarAssignmentForCalendar(UUID bankId, UUID calendarId, UUID assignmentId);

    /**
     * Retrieves a calendar assignment by its unique identifier.
     *
     * @param calendarAssignmentId the unique identifier of the calendar assignment to retrieve
     * @return a Mono emitting the {@link CalendarAssignmentDTO} representing the calendar assignment if found,
     *         or an empty Mono if the calendar assignment does not exist
     */
    Mono<CalendarAssignmentDTO> getCalendarAssignmentById(UUID calendarAssignmentId);

    /**
     * Retrieves a calendar assignment for a specific working calendar by its unique identifier.
     *
     * @param bankId the unique identifier of the bank
     * @param calendarId the unique identifier of the calendar
     * @param assignmentId the unique identifier of the assignment to retrieve
     * @return a Mono emitting the {@link CalendarAssignmentDTO} representing the calendar assignment if found,
     *         or an empty Mono if the calendar assignment does not exist or doesn't belong to the specified calendar
     */
    Mono<CalendarAssignmentDTO> getCalendarAssignmentByIdForCalendar(UUID bankId, UUID calendarId, UUID assignmentId);
}
