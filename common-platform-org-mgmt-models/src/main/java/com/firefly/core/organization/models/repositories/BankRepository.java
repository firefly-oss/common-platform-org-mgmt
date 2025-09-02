package com.firefly.core.organization.models.repositories;

import com.firefly.core.organization.models.entities.Bank;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing {@link Bank} entities.
 */
@Repository
public interface BankRepository extends BaseRepository<Bank, UUID> {
    
    /**
     * Find a bank by its code.
     *
     * @param code the bank code
     * @return a Mono emitting the bank if found, or empty if not found
     */
    Mono<Bank> findByCode(String code);
    
    /**
     * Find all active banks.
     *
     * @return a Flux emitting all active banks
     */
    Flux<Bank> findByIsActiveTrue();
    
    /**
     * Find a bank by its name.
     *
     * @param name the bank name
     * @return a Mono emitting the bank if found, or empty if not found
     */
    Mono<Bank> findByName(String name);
}