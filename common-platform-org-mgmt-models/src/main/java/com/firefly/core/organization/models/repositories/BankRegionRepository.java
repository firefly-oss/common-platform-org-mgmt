package com.firefly.core.organization.models.repositories;

import com.firefly.core.organization.models.entities.BankRegion;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing {@link BankRegion} entities.
 */
@Repository
public interface BankRegionRepository extends BaseRepository<BankRegion, UUID> {
    
    /**
     * Find all regions belonging to a specific division.
     *
     * @param divisionId the division ID
     * @return a Flux emitting all regions for the specified division
     */
    Flux<BankRegion> findByDivisionId(UUID divisionId);
    
    /**
     * Find a region by its division ID and code.
     *
     * @param divisionId the division ID
     * @param code the region code
     * @return a Mono emitting the region if found, or empty if not found
     */
    Mono<BankRegion> findByDivisionIdAndCode(UUID divisionId, String code);
    
    /**
     * Find all active regions belonging to a specific division.
     *
     * @param divisionId the division ID
     * @return a Flux emitting all active regions for the specified division
     */
    Flux<BankRegion> findByDivisionIdAndIsActiveTrue(UUID divisionId);
    
    /**
     * Find a region by its name.
     *
     * @param name the region name
     * @return a Mono emitting the region if found, or empty if not found
     */
    Mono<BankRegion> findByName(String name);
}