package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BankDivisionMapper;
import com.firefly.core.organization.interfaces.dtos.BankDivisionDTO;
import com.firefly.core.organization.models.entities.BankDivision;
import com.firefly.core.organization.models.repositories.BankDivisionRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BankDivisionServiceImplTest {

    @Mock
    private BankDivisionRepository bankDivisionRepository;

    @Mock
    private BankDivisionMapper bankDivisionMapper;

    @InjectMocks
    private BankDivisionServiceImpl bankDivisionService;

    private BankDivisionDTO bankDivisionDTO;
    private BankDivision bankDivision;

    @BeforeEach
    void setUp() {
        // Setup test data
        bankDivisionDTO = BankDivisionDTO.builder()
                .id(1L)
                .bankId(1L)
                .code("TEST_DIVISION")
                .name("Test Division")
                .description("Test Division Description")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        bankDivision = BankDivision.builder()
                .id(1L)
                .bankId(1L)
                .code("TEST_DIVISION")
                .name("Test Division")
                .description("Test Division Description")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();
    }


    @Test
    void createBankDivision_ShouldCreateAndReturnBankDivision() {
        // Arrange
        when(bankDivisionMapper.toEntity(bankDivisionDTO)).thenReturn(bankDivision);
        when(bankDivisionRepository.save(bankDivision)).thenReturn(Mono.just(bankDivision));
        when(bankDivisionMapper.toDTO(bankDivision)).thenReturn(bankDivisionDTO);

        // Act & Assert
        StepVerifier.create(bankDivisionService.createBankDivision(bankDivisionDTO))
                .expectNext(bankDivisionDTO)
                .verifyComplete();

        verify(bankDivisionMapper).toEntity(bankDivisionDTO);
        verify(bankDivisionRepository).save(bankDivision);
        verify(bankDivisionMapper).toDTO(bankDivision);
    }

    @Test
    void updateBankDivision_WhenBankDivisionExists_ShouldUpdateAndReturnBankDivision() {
        // Arrange
        UUID bankDivisionId = 1L;
        when(bankDivisionRepository.findById(bankDivisionId)).thenReturn(Mono.just(bankDivision));
        when(bankDivisionMapper.toEntity(bankDivisionDTO)).thenReturn(bankDivision);
        when(bankDivisionRepository.save(bankDivision)).thenReturn(Mono.just(bankDivision));
        when(bankDivisionMapper.toDTO(bankDivision)).thenReturn(bankDivisionDTO);

        // Act & Assert
        StepVerifier.create(bankDivisionService.updateBankDivision(bankDivisionId, bankDivisionDTO))
                .expectNext(bankDivisionDTO)
                .verifyComplete();

        verify(bankDivisionRepository).findById(bankDivisionId);
        verify(bankDivisionMapper).toEntity(bankDivisionDTO);
        verify(bankDivisionRepository).save(bankDivision);
        verify(bankDivisionMapper).toDTO(bankDivision);
    }

    @Test
    void updateBankDivision_WhenBankDivisionDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID bankDivisionId = 1L;
        when(bankDivisionRepository.findById(bankDivisionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankDivisionService.updateBankDivision(bankDivisionId, bankDivisionDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank division not found with ID: " + bankDivisionId))
                .verify();

        verify(bankDivisionRepository).findById(bankDivisionId);
    }

    @Test
    void deleteBankDivision_WhenBankDivisionExists_ShouldDeleteBankDivision() {
        // Arrange
        UUID bankDivisionId = 1L;
        when(bankDivisionRepository.findById(bankDivisionId)).thenReturn(Mono.just(bankDivision));
        when(bankDivisionRepository.deleteById(bankDivisionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankDivisionService.deleteBankDivision(bankDivisionId))
                .verifyComplete();

        verify(bankDivisionRepository).findById(bankDivisionId);
        verify(bankDivisionRepository).deleteById(bankDivisionId);
    }

    @Test
    void deleteBankDivision_WhenBankDivisionDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID bankDivisionId = 1L;
        when(bankDivisionRepository.findById(bankDivisionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankDivisionService.deleteBankDivision(bankDivisionId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank division not found with ID: " + bankDivisionId))
                .verify();

        verify(bankDivisionRepository).findById(bankDivisionId);
    }

    @Test
    void getBankDivisionById_WhenBankDivisionExists_ShouldReturnBankDivision() {
        // Arrange
        UUID bankDivisionId = 1L;
        when(bankDivisionRepository.findById(bankDivisionId)).thenReturn(Mono.just(bankDivision));
        when(bankDivisionMapper.toDTO(bankDivision)).thenReturn(bankDivisionDTO);

        // Act & Assert
        StepVerifier.create(bankDivisionService.getBankDivisionById(bankDivisionId))
                .expectNext(bankDivisionDTO)
                .verifyComplete();

        verify(bankDivisionRepository).findById(bankDivisionId);
        verify(bankDivisionMapper).toDTO(bankDivision);
    }

    @Test
    void getBankDivisionById_WhenBankDivisionDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID bankDivisionId = 1L;
        when(bankDivisionRepository.findById(bankDivisionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankDivisionService.getBankDivisionById(bankDivisionId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank division not found with ID: " + bankDivisionId))
                .verify();

        verify(bankDivisionRepository).findById(bankDivisionId);
    }
}
