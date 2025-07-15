package com.catalis.core.organization.models.repositories;

import com.catalis.core.organization.models.entities.BankHoliday;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * Repository for managing {@link BankHoliday} entities.
 */
@Repository
public interface BankHolidayRepository extends BaseRepository<BankHoliday, Long> {
    
    /**
     * Find all holidays for a specific bank.
     *
     * @param bankId the bank ID
     * @return a Flux emitting all holidays for the specified bank
     */
    Flux<BankHoliday> findByBankId(Long bankId);
    
    /**
     * Find all holidays for a specific branch.
     *
     * @param branchId the branch ID
     * @return a Flux emitting all holidays for the specified branch
     */
    Flux<BankHoliday> findByBranchId(Long branchId);
    
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
    Flux<BankHoliday> findByBankIdAndIsRecurringTrue(Long bankId);
    
    /**
     * Find all holidays for a specific bank and date.
     *
     * @param bankId the bank ID
     * @param date the date
     * @return a Flux emitting all holidays for the specified bank and date
     */
    Flux<BankHoliday> findByBankIdAndDate(Long bankId, LocalDate date);
    
    /**
     * Find all holidays for a specific branch and date.
     *
     * @param branchId the branch ID
     * @param date the date
     * @return a Flux emitting all holidays for the specified branch and date
     */
    Flux<BankHoliday> findByBranchIdAndDate(Long branchId, LocalDate date);
}