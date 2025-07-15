package com.catalis.core.organization.models.repositories;

import com.catalis.core.organization.models.entities.Branch;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository for managing {@link Branch} entities.
 */
@Repository
public interface BranchRepository extends BaseRepository<Branch, Long> {
    
    /**
     * Find all branches belonging to a specific bank.
     *
     * @param bankId the bank ID
     * @return a Flux emitting all branches for the specified bank
     */
    Flux<Branch> findByBankId(Long bankId);
    
    /**
     * Find all branches belonging to a specific region.
     *
     * @param regionId the region ID
     * @return a Flux emitting all branches for the specified region
     */
    Flux<Branch> findByRegionId(Long regionId);
    
    /**
     * Find a branch by its bank ID and code.
     *
     * @param bankId the bank ID
     * @param code the branch code
     * @return a Mono emitting the branch if found, or empty if not found
     */
    Mono<Branch> findByBankIdAndCode(Long bankId, String code);
    
    /**
     * Find all active branches belonging to a specific bank.
     *
     * @param bankId the bank ID
     * @return a Flux emitting all active branches for the specified bank
     */
    Flux<Branch> findByBankIdAndIsActiveTrue(Long bankId);
    
    /**
     * Find all active branches belonging to a specific region.
     *
     * @param regionId the region ID
     * @return a Flux emitting all active branches for the specified region
     */
    Flux<Branch> findByRegionIdAndIsActiveTrue(Long regionId);
    
    /**
     * Find a branch by its name.
     *
     * @param name the branch name
     * @return a Mono emitting the branch if found, or empty if not found
     */
    Mono<Branch> findByName(String name);
}