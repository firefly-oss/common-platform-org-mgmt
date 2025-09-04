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


package com.firefly.core.organization.models.repositories;

import com.firefly.core.organization.models.entities.WorkingCalendar;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing {@link WorkingCalendar} entities.
 */
@Repository
public interface WorkingCalendarRepository extends BaseRepository<WorkingCalendar, UUID> {
    
    /**
     * Find all calendars belonging to a specific bank.
     *
     * @param bankId the bank ID
     * @return a Flux emitting all calendars for the specified bank
     */
    Flux<WorkingCalendar> findByBankId(UUID bankId);
    
    /**
     * Find the default calendar for a specific bank.
     *
     * @param bankId the bank ID
     * @return a Mono emitting the default calendar if found, or empty if not found
     */
    Mono<WorkingCalendar> findByBankIdAndIsDefaultTrue(UUID bankId);
    
    /**
     * Find a calendar by its name and bank ID.
     *
     * @param name the calendar name
     * @param bankId the bank ID
     * @return a Mono emitting the calendar if found, or empty if not found
     */
    Mono<WorkingCalendar> findByNameAndBankId(String name, UUID bankId);
    
    /**
     * Find all calendars for a specific time zone.
     *
     * @param timeZoneId the time zone ID
     * @return a Flux emitting all calendars for the specified time zone
     */
    Flux<WorkingCalendar> findByTimeZoneId(UUID timeZoneId);
}