package com.firefly.core.organization.models.repositories;

import com.firefly.core.organization.interfaces.enums.DayOfWeek;
import com.firefly.core.organization.models.entities.BranchHours;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository for managing {@link BranchHours} entities.
 */
@Repository
public interface BranchHoursRepository extends BaseRepository<BranchHours, Long> {
    
    /**
     * Find all hours for a specific branch.
     *
     * @param branchId the branch ID
     * @return a Flux emitting all hours for the specified branch
     */
    Flux<BranchHours> findByBranchId(Long branchId);
    
    /**
     * Find hours for a specific branch and day of week.
     *
     * @param branchId the branch ID
     * @param dayOfWeek the day of week
     * @return a Mono emitting the hours if found, or empty if not found
     */
    Mono<BranchHours> findByBranchIdAndDayOfWeek(Long branchId, DayOfWeek dayOfWeek);
    
    /**
     * Find all closed days for a specific branch.
     *
     * @param branchId the branch ID
     * @return a Flux emitting all closed days for the specified branch
     */
    Flux<BranchHours> findByBranchIdAndIsClosedTrue(Long branchId);
    
    /**
     * Find all open days for a specific branch.
     *
     * @param branchId the branch ID
     * @return a Flux emitting all open days for the specified branch
     */
    Flux<BranchHours> findByBranchIdAndIsClosedFalse(Long branchId);
    
    /**
     * Find all hours for a specific day of week across all branches.
     *
     * @param dayOfWeek the day of week
     * @return a Flux emitting all hours for the specified day of week
     */
    Flux<BranchHours> findByDayOfWeek(DayOfWeek dayOfWeek);
}