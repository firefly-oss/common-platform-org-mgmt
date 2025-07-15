package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.BranchMapper;
import com.catalis.core.organization.interfaces.dtos.BranchDTO;
import com.catalis.core.organization.models.entities.Branch;
import com.catalis.core.organization.models.repositories.BranchRepository;
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
public class BranchServiceImplTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private BranchMapper branchMapper;

    @InjectMocks
    private BranchServiceImpl branchService;

    private BranchDTO branchDTO;
    private Branch branch;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();

        branchDTO = BranchDTO.builder()
                .id(1L)
                .bankId(1L)
                .regionId(1L)
                .code("BR001")
                .name("Main Branch")
                .description("Main Branch of Test Bank")
                .phoneNumber("123-456-7890")
                .email("main@testbank.com")
                .addressLine("123 Main St")
                .postalCode("12345")
                .city("Test City")
                .state("Test State")
                .countryId(1L)
                .isActive(true)
                .openedAt(now.minusYears(5))
                .createdAt(now)
                .build();

        branch = Branch.builder()
                .id(1L)
                .bankId(1L)
                .regionId(1L)
                .code("BR001")
                .name("Main Branch")
                .description("Main Branch of Test Bank")
                .phoneNumber("123-456-7890")
                .email("main@testbank.com")
                .addressLine("123 Main St")
                .postalCode("12345")
                .city("Test City")
                .state("Test State")
                .countryId(1L)
                .isActive(true)
                .openedAt(now.minusYears(5))
                .createdAt(now)
                .build();
    }


    @Test
    void createBranch_ShouldCreateAndReturnBranch() {
        // Arrange
        when(branchMapper.toEntity(branchDTO)).thenReturn(branch);
        when(branchRepository.save(branch)).thenReturn(Mono.just(branch));
        when(branchMapper.toDTO(branch)).thenReturn(branchDTO);

        // Act & Assert
        StepVerifier.create(branchService.createBranch(branchDTO))
                .expectNext(branchDTO)
                .verifyComplete();

        verify(branchMapper).toEntity(branchDTO);
        verify(branchRepository).save(branch);
        verify(branchMapper).toDTO(branch);
    }

    @Test
    void updateBranch_WhenBranchExists_ShouldUpdateAndReturnBranch() {
        // Arrange
        Long branchId = 1L;
        when(branchRepository.findById(branchId)).thenReturn(Mono.just(branch));
        when(branchMapper.toEntity(branchDTO)).thenReturn(branch);
        when(branchRepository.save(branch)).thenReturn(Mono.just(branch));
        when(branchMapper.toDTO(branch)).thenReturn(branchDTO);

        // Act & Assert
        StepVerifier.create(branchService.updateBranch(branchId, branchDTO))
                .expectNext(branchDTO)
                .verifyComplete();

        verify(branchRepository).findById(branchId);
        verify(branchMapper).toEntity(branchDTO);
        verify(branchRepository).save(branch);
        verify(branchMapper).toDTO(branch);
    }

    @Test
    void updateBranch_WhenBranchDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchId = 1L;
        when(branchRepository.findById(branchId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchService.updateBranch(branchId, branchDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch not found with ID: " + branchId))
                .verify();

        verify(branchRepository).findById(branchId);
    }

    @Test
    void deleteBranch_WhenBranchExists_ShouldDeleteBranch() {
        // Arrange
        Long branchId = 1L;
        when(branchRepository.findById(branchId)).thenReturn(Mono.just(branch));
        when(branchRepository.deleteById(branchId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchService.deleteBranch(branchId))
                .verifyComplete();

        verify(branchRepository).findById(branchId);
        verify(branchRepository).deleteById(branchId);
    }

    @Test
    void deleteBranch_WhenBranchDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchId = 1L;
        when(branchRepository.findById(branchId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchService.deleteBranch(branchId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch not found with ID: " + branchId))
                .verify();

        verify(branchRepository).findById(branchId);
    }

    @Test
    void getBranchById_WhenBranchExists_ShouldReturnBranch() {
        // Arrange
        Long branchId = 1L;
        when(branchRepository.findById(branchId)).thenReturn(Mono.just(branch));
        when(branchMapper.toDTO(branch)).thenReturn(branchDTO);

        // Act & Assert
        StepVerifier.create(branchService.getBranchById(branchId))
                .expectNext(branchDTO)
                .verifyComplete();

        verify(branchRepository).findById(branchId);
        verify(branchMapper).toDTO(branch);
    }

    @Test
    void getBranchById_WhenBranchDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchId = 1L;
        when(branchRepository.findById(branchId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchService.getBranchById(branchId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch not found with ID: " + branchId))
                .verify();

        verify(branchRepository).findById(branchId);
    }
}
