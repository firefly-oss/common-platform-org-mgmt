package com.firefly.core.organization.models.repositories;

import com.firefly.core.organization.models.entities.BranchDepartment;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing {@link BranchDepartment} entities.
 */
@Repository
public interface BranchDepartmentRepository extends BaseRepository<BranchDepartment, UUID> {
    
    /**
     * Find all departments belonging to a specific branch.
     *
     * @param branchId the branch ID
     * @return a Flux emitting all departments for the specified branch
     */
    Flux<BranchDepartment> findByBranchId(UUID branchId);
    
    /**
     * Find all active departments belonging to a specific branch.
     *
     * @param branchId the branch ID
     * @return a Flux emitting all active departments for the specified branch
     */
    Flux<BranchDepartment> findByBranchIdAndIsActiveTrue(UUID branchId);
    
    /**
     * Find a department by its name and branch ID.
     *
     * @param name the department name
     * @param branchId the branch ID
     * @return a Mono emitting the department if found, or empty if not found
     */
    Mono<BranchDepartment> findByNameAndBranchId(String name, UUID branchId);
    
    /**
     * Find a department by its name.
     *
     * @param name the department name
     * @return a Mono emitting the department if found, or empty if not found
     */
    Mono<BranchDepartment> findByName(String name);
}