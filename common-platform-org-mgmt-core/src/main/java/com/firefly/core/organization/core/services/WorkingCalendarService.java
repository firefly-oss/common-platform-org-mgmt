/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.interfaces.dtos.WorkingCalendarDTO;

import reactor.core.publisher.Mono;
import java.util.UUID;

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
    Mono<PaginationResponse<WorkingCalendarDTO>> filterWorkingCalendarsForBank(UUID bankId, FilterRequest<WorkingCalendarDTO> filterRequest);

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
    Mono<WorkingCalendarDTO> createWorkingCalendarForBank(UUID bankId, WorkingCalendarDTO workingCalendarDTO);

    /**
     * Updates an existing working calendar with updated information.
     *
     * @param workingCalendarId the unique identifier of the working calendar to be updated
     * @param workingCalendarDTO the data transfer object containing the updated details of the working calendar
     * @return a reactive Mono containing the updated WorkingCalendarDTO
     */
    Mono<WorkingCalendarDTO> updateWorkingCalendar(UUID workingCalendarId, WorkingCalendarDTO workingCalendarDTO);

    /**
     * Updates an existing working calendar for a specific bank with updated information.
     *
     * @param bankId the unique identifier of the bank
     * @param calendarId the unique identifier of the calendar to be updated
     * @param workingCalendarDTO the data transfer object containing the updated details of the working calendar
     * @return a reactive Mono containing the updated WorkingCalendarDTO
     */
    Mono<WorkingCalendarDTO> updateWorkingCalendarForBank(UUID bankId, UUID calendarId, WorkingCalendarDTO workingCalendarDTO);

    /**
     * Deletes a working calendar identified by its unique ID.
     *
     * @param workingCalendarId the unique identifier of the working calendar to be deleted
     * @return a Mono that completes when the working calendar is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteWorkingCalendar(UUID workingCalendarId);

    /**
     * Deletes a working calendar for a specific bank identified by its unique ID.
     *
     * @param bankId the unique identifier of the bank
     * @param calendarId the unique identifier of the calendar to be deleted
     * @return a Mono that completes when the working calendar is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteWorkingCalendarForBank(UUID bankId, UUID calendarId);

    /**
     * Retrieves a working calendar by its unique identifier.
     *
     * @param workingCalendarId the unique identifier of the working calendar to retrieve
     * @return a Mono emitting the {@link WorkingCalendarDTO} representing the working calendar if found,
     *         or an empty Mono if the working calendar does not exist
     */
    Mono<WorkingCalendarDTO> getWorkingCalendarById(UUID workingCalendarId);

    /**
     * Retrieves a working calendar for a specific bank by its unique identifier.
     *
     * @param bankId the unique identifier of the bank
     * @param calendarId the unique identifier of the calendar to retrieve
     * @return a Mono emitting the {@link WorkingCalendarDTO} representing the working calendar if found,
     *         or an empty Mono if the working calendar does not exist or doesn't belong to the specified bank
     */
    Mono<WorkingCalendarDTO> getWorkingCalendarByIdForBank(UUID bankId, UUID calendarId);
}
