package com.catalis.core.organization.models.repositories;

import com.catalis.core.organization.models.entities.CalendarAssignment;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Repository for managing {@link CalendarAssignment} entities.
 */
@Repository
public interface CalendarAssignmentRepository extends BaseRepository<CalendarAssignment, Long> {
    
    /**
     * Find all assignments for a specific calendar.
     *
     * @param calendarId the calendar ID
     * @return a Flux emitting all assignments for the specified calendar
     */
    Flux<CalendarAssignment> findByCalendarId(Long calendarId);
    
    /**
     * Find all assignments for a specific branch.
     *
     * @param branchId the branch ID
     * @return a Flux emitting all assignments for the specified branch
     */
    Flux<CalendarAssignment> findByBranchId(Long branchId);
    
    /**
     * Find all assignments for a specific department.
     *
     * @param departmentId the department ID
     * @return a Flux emitting all assignments for the specified department
     */
    Flux<CalendarAssignment> findByDepartmentId(Long departmentId);
    
    /**
     * Find all assignments for a specific position.
     *
     * @param positionId the position ID
     * @return a Flux emitting all assignments for the specified position
     */
    Flux<CalendarAssignment> findByPositionId(Long positionId);
    
    /**
     * Find all active assignments for a specific calendar.
     *
     * @param calendarId the calendar ID
     * @return a Flux emitting all active assignments for the specified calendar
     */
    Flux<CalendarAssignment> findByCalendarIdAndIsActiveTrue(Long calendarId);
    
    /**
     * Find all active assignments for a specific branch.
     *
     * @param branchId the branch ID
     * @return a Flux emitting all active assignments for the specified branch
     */
    Flux<CalendarAssignment> findByBranchIdAndIsActiveTrue(Long branchId);
    
    /**
     * Find all assignments effective at a specific date.
     *
     * @param date the date to check
     * @return a Flux emitting all assignments effective at the specified date
     */
    Flux<CalendarAssignment> findByEffectiveFromBeforeAndEffectiveToAfterOrEffectiveToIsNull(
            LocalDateTime date, LocalDateTime sameDate);
}