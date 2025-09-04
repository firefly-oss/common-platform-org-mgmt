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

import com.firefly.core.organization.models.entities.BankHoliday;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Repository for managing {@link BankHoliday} entities.
 */
@Repository
public interface BankHolidayRepository extends BaseRepository<BankHoliday, UUID> {
    
    /**
     * Find all holidays for a specific bank.
     *
     * @param bankId the bank ID
     * @return a Flux emitting all holidays for the specified bank
     */
    Flux<BankHoliday> findByBankId(UUID bankId);
    
    /**
     * Find all holidays for a specific branch.
     *
     * @param branchId the branch ID
     * @return a Flux emitting all holidays for the specified branch
     */
    Flux<BankHoliday> findByBranchId(UUID branchId);
    
    /**
     * Find all holidays for a specific date.
     *
     * @param date the date
     * @return a Flux emitting all holidays for the specified date
     */
    Flux<BankHoliday> findByDate(LocalDate date);
    
    /**
     * Find all recurring holidays for a specific bank.
     *
     * @param bankId the bank ID
     * @return a Flux emitting all recurring holidays for the specified bank
     */
    Flux<BankHoliday> findByBankIdAndIsRecurringTrue(UUID bankId);
    
    /**
     * Find all holidays for a specific bank and date.
     *
     * @param bankId the bank ID
     * @param date the date
     * @return a Flux emitting all holidays for the specified bank and date
     */
    Flux<BankHoliday> findByBankIdAndDate(UUID bankId, LocalDate date);
    
    /**
     * Find all holidays for a specific branch and date.
     *
     * @param branchId the branch ID
     * @param date the date
     * @return a Flux emitting all holidays for the specified branch and date
     */
    Flux<BankHoliday> findByBranchIdAndDate(UUID branchId, LocalDate date);
}