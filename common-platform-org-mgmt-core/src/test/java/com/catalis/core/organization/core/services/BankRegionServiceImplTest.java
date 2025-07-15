package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.BankRegionMapper;
import com.catalis.core.organization.interfaces.dtos.BankRegionDTO;
import com.catalis.core.organization.models.entities.BankRegion;
import com.catalis.core.organization.models.repositories.BankRegionRepository;
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

@ExtendWith(MockitoExtension.class)
public class BankRegionServiceImplTest {

    @Mock
    private BankRegionRepository bankRegionRepository;

    @Mock
    private BankRegionMapper bankRegionMapper;

    @InjectMocks
    private BankRegionServiceImpl bankRegionService;

    private BankRegionDTO bankRegionDTO;
    private BankRegion bankRegion;

    @BeforeEach
    void setUp() {
        // Setup test data
        bankRegionDTO = BankRegionDTO.builder()
                .id(1L)
                .divisionId(1L)
                .code("TEST_REGION")
                .name("Test Region")
                .description("Test Region Description")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        bankRegion = BankRegion.builder()
                .id(1L)
                .divisionId(1L)
                .code("TEST_REGION")
                .name("Test Region")
                .description("Test Region Description")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();
    }


    @Test
    void createBankRegion_ShouldCreateAndReturnBankRegion() {
        // Arrange
        when(bankRegionMapper.toEntity(bankRegionDTO)).thenReturn(bankRegion);
        when(bankRegionRepository.save(bankRegion)).thenReturn(Mono.just(bankRegion));
        when(bankRegionMapper.toDTO(bankRegion)).thenReturn(bankRegionDTO);

        // Act & Assert
        StepVerifier.create(bankRegionService.createBankRegion(bankRegionDTO))
                .expectNext(bankRegionDTO)
                .verifyComplete();

        verify(bankRegionMapper).toEntity(bankRegionDTO);
        verify(bankRegionRepository).save(bankRegion);
        verify(bankRegionMapper).toDTO(bankRegion);
    }

    @Test
    void updateBankRegion_WhenBankRegionExists_ShouldUpdateAndReturnBankRegion() {
        // Arrange
        Long bankRegionId = 1L;
        when(bankRegionRepository.findById(bankRegionId)).thenReturn(Mono.just(bankRegion));
        when(bankRegionMapper.toEntity(bankRegionDTO)).thenReturn(bankRegion);
        when(bankRegionRepository.save(bankRegion)).thenReturn(Mono.just(bankRegion));
        when(bankRegionMapper.toDTO(bankRegion)).thenReturn(bankRegionDTO);

        // Act & Assert
        StepVerifier.create(bankRegionService.updateBankRegion(bankRegionId, bankRegionDTO))
                .expectNext(bankRegionDTO)
                .verifyComplete();

        verify(bankRegionRepository).findById(bankRegionId);
        verify(bankRegionMapper).toEntity(bankRegionDTO);
        verify(bankRegionRepository).save(bankRegion);
        verify(bankRegionMapper).toDTO(bankRegion);
    }

    @Test
    void updateBankRegion_WhenBankRegionDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankRegionId = 1L;
        when(bankRegionRepository.findById(bankRegionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankRegionService.updateBankRegion(bankRegionId, bankRegionDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank region not found with ID: " + bankRegionId))
                .verify();

        verify(bankRegionRepository).findById(bankRegionId);
    }

    @Test
    void deleteBankRegion_WhenBankRegionExists_ShouldDeleteBankRegion() {
        // Arrange
        Long bankRegionId = 1L;
        when(bankRegionRepository.findById(bankRegionId)).thenReturn(Mono.just(bankRegion));
        when(bankRegionRepository.deleteById(bankRegionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankRegionService.deleteBankRegion(bankRegionId))
                .verifyComplete();

        verify(bankRegionRepository).findById(bankRegionId);
        verify(bankRegionRepository).deleteById(bankRegionId);
    }

    @Test
    void deleteBankRegion_WhenBankRegionDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankRegionId = 1L;
        when(bankRegionRepository.findById(bankRegionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankRegionService.deleteBankRegion(bankRegionId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank region not found with ID: " + bankRegionId))
                .verify();

        verify(bankRegionRepository).findById(bankRegionId);
    }

    @Test
    void getBankRegionById_WhenBankRegionExists_ShouldReturnBankRegion() {
        // Arrange
        Long bankRegionId = 1L;
        when(bankRegionRepository.findById(bankRegionId)).thenReturn(Mono.just(bankRegion));
        when(bankRegionMapper.toDTO(bankRegion)).thenReturn(bankRegionDTO);

        // Act & Assert
        StepVerifier.create(bankRegionService.getBankRegionById(bankRegionId))
                .expectNext(bankRegionDTO)
                .verifyComplete();

        verify(bankRegionRepository).findById(bankRegionId);
        verify(bankRegionMapper).toDTO(bankRegion);
    }

    @Test
    void getBankRegionById_WhenBankRegionDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankRegionId = 1L;
        when(bankRegionRepository.findById(bankRegionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankRegionService.getBankRegionById(bankRegionId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank region not found with ID: " + bankRegionId))
                .verify();

        verify(bankRegionRepository).findById(bankRegionId);
    }
}
