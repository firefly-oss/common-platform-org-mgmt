package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BankMapper;
import com.firefly.core.organization.interfaces.dtos.BankDTO;
import com.firefly.core.organization.models.entities.Bank;
import com.firefly.core.organization.models.repositories.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceImplTest {

    @Mock
    private BankRepository bankRepository;

    @Mock
    private BankMapper bankMapper;

    @InjectMocks
    private BankServiceImpl bankService;

    private BankDTO bankDTO;
    private Bank bank;

    @BeforeEach
    void setUp() {
        // Setup test data
        bankDTO = BankDTO.builder()
                .id(1L)
                .code("TEST")
                .name("Test Bank")
                .description("Test Bank Description")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        bank = Bank.builder()
                .id(1L)
                .code("TEST")
                .name("Test Bank")
                .description("Test Bank Description")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();
    }


    @Test
    void createBank_ShouldCreateAndReturnBank() {
        // Arrange
        when(bankMapper.toEntity(bankDTO)).thenReturn(bank);
        when(bankRepository.save(bank)).thenReturn(Mono.just(bank));
        when(bankMapper.toDTO(bank)).thenReturn(bankDTO);

        // Act & Assert
        StepVerifier.create(bankService.createBank(bankDTO))
                .expectNext(bankDTO)
                .verifyComplete();

        verify(bankMapper).toEntity(bankDTO);
        verify(bankRepository).save(bank);
        verify(bankMapper).toDTO(bank);
    }

    @Test
    void updateBank_WhenBankExists_ShouldUpdateAndReturnBank() {
        // Arrange
        Long bankId = 1L;
        when(bankRepository.findById(bankId)).thenReturn(Mono.just(bank));
        when(bankMapper.toEntity(bankDTO)).thenReturn(bank);
        when(bankRepository.save(bank)).thenReturn(Mono.just(bank));
        when(bankMapper.toDTO(bank)).thenReturn(bankDTO);

        // Act & Assert
        StepVerifier.create(bankService.updateBank(bankId, bankDTO))
                .expectNext(bankDTO)
                .verifyComplete();

        verify(bankRepository).findById(bankId);
        verify(bankMapper).toEntity(bankDTO);
        verify(bankRepository).save(bank);
        verify(bankMapper).toDTO(bank);
    }

    @Test
    void updateBank_WhenBankDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankId = 1L;
        when(bankRepository.findById(bankId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankService.updateBank(bankId, bankDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank not found with ID: " + bankId))
                .verify();

        verify(bankRepository).findById(bankId);
    }

    @Test
    void deleteBank_WhenBankExists_ShouldDeleteBank() {
        // Arrange
        Long bankId = 1L;
        when(bankRepository.findById(bankId)).thenReturn(Mono.just(bank));
        when(bankRepository.deleteById(bankId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankService.deleteBank(bankId))
                .verifyComplete();

        verify(bankRepository).findById(bankId);
        verify(bankRepository).deleteById(bankId);
    }

    @Test
    void deleteBank_WhenBankDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankId = 1L;
        when(bankRepository.findById(bankId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankService.deleteBank(bankId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank not found with ID: " + bankId))
                .verify();

        verify(bankRepository).findById(bankId);
    }

    @Test
    void getBankById_WhenBankExists_ShouldReturnBank() {
        // Arrange
        Long bankId = 1L;
        when(bankRepository.findById(bankId)).thenReturn(Mono.just(bank));
        when(bankMapper.toDTO(bank)).thenReturn(bankDTO);

        // Act & Assert
        StepVerifier.create(bankService.getBankById(bankId))
                .expectNext(bankDTO)
                .verifyComplete();

        verify(bankRepository).findById(bankId);
        verify(bankMapper).toDTO(bank);
    }

    @Test
    void getBankById_WhenBankDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankId = 1L;
        when(bankRepository.findById(bankId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankService.getBankById(bankId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank not found with ID: " + bankId))
                .verify();

        verify(bankRepository).findById(bankId);
    }
}
