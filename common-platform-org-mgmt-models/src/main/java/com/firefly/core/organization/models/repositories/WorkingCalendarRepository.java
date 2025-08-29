package com.firefly.core.organization.models.repositories;

import com.firefly.core.organization.models.entities.WorkingCalendar;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository for managing {@link WorkingCalendar} entities.
 */
@Repository
public interface WorkingCalendarRepository extends BaseRepository<WorkingCalendar, Long> {
    
    /**
     * Find all calendars belonging to a specific bank.
     *
     * @param bankId the bank ID
     * @return a Flux emitting all calendars for the specified bank
     */
    Flux<WorkingCalendar> findByBankId(Long bankId);
    
    /**
     * Find the default calendar for a specific bank.
     *
     * @param bankId the bank ID
     * @return a Mono emitting the default calendar if found, or empty if not found
     */
    Mono<WorkingCalendar> findByBankIdAndIsDefaultTrue(Long bankId);
    
    /**
     * Find a calendar by its name and bank ID.
     *
     * @param name the calendar name
     * @param bankId the bank ID
     * @return a Mono emitting the calendar if found, or empty if not found
     */
    Mono<WorkingCalendar> findByNameAndBankId(String name, Long bankId);
    
    /**
     * Find all calendars for a specific time zone.
     *
     * @param timeZoneId the time zone ID
     * @return a Flux emitting all calendars for the specified time zone
     */
    Flux<WorkingCalendar> findByTimeZoneId(Long timeZoneId);
}