package com.catalis.core.organization.models.repositories;

import com.catalis.core.organization.models.entities.BankDivision;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository for managing {@link BankDivision} entities.
 */
@Repository
public interface BankDivisionRepository extends BaseRepository<BankDivision, Long> {
    
    /**
     * Find all divisions belonging to a specific bank.
     *
     * @param bankId the bank ID
     * @return a Flux emitting all divisions for the specified bank
     */
    Flux<BankDivision> findByBankId(Long bankId);
    
    /**
     * Find a division by its bank ID and code.
     *
     * @param bankId the bank ID
     * @param code the division code
     * @return a Mono emitting the division if found, or empty if not found
     */
    Mono<BankDivision> findByBankIdAndCode(Long bankId, String code);
    
    /**
     * Find all active divisions belonging to a specific bank.
     *
     * @param bankId the bank ID
     * @return a Flux emitting all active divisions for the specified bank
     */
    Flux<BankDivision> findByBankIdAndIsActiveTrue(Long bankId);
    
    /**
     * Find a division by its name.
     *
     * @param name the division name
     * @return a Mono emitting the division if found, or empty if not found
     */
    Mono<BankDivision> findByName(String name);
}