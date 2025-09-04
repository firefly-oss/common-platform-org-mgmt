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