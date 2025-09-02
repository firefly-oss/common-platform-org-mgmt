package com.firefly.core.organization.models.repositories;

import com.firefly.core.organization.models.entities.BranchPosition;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing {@link BranchPosition} entities.
 */
@Repository
public interface BranchPositionRepository extends BaseRepository<BranchPosition, UUID> {
    
    /**
     * Find all positions belonging to a specific department.
     *
     * @param departmentId the department ID
     * @return a Flux emitting all positions for the specified department
     */
    Flux<BranchPosition> findByDepartmentId(UUID departmentId);
    
    /**
     * Find all active positions belonging to a specific department.
     *
     * @param departmentId the department ID
     * @return a Flux emitting all active positions for the specified department
     */
    Flux<BranchPosition> findByDepartmentIdAndIsActiveTrue(UUID departmentId);
    
    /**
     * Find a position by its title and department ID.
     *
     * @param title the position title
     * @param departmentId the department ID
     * @return a Mono emitting the position if found, or empty if not found
     */
    Mono<BranchPosition> findByTitleAndDepartmentId(String title, UUID departmentId);
    
    /**
     * Find a position by its title.
     *
     * @param title the position title
     * @return a Mono emitting the position if found, or empty if not found
     */
    Mono<BranchPosition> findByTitle(String title);
}