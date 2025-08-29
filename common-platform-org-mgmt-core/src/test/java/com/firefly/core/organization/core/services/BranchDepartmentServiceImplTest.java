package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BranchDepartmentMapper;
import com.firefly.core.organization.interfaces.dtos.BranchDepartmentDTO;
import com.firefly.core.organization.models.entities.BranchDepartment;
import com.firefly.core.organization.models.repositories.BranchDepartmentRepository;
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
public class BranchDepartmentServiceImplTest {

    @Mock
    private BranchDepartmentRepository branchDepartmentRepository;

    @Mock
    private BranchDepartmentMapper branchDepartmentMapper;

    @InjectMocks
    private BranchDepartmentServiceImpl branchDepartmentService;

    private BranchDepartmentDTO branchDepartmentDTO;
    private BranchDepartment branchDepartment;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        
        branchDepartmentDTO = BranchDepartmentDTO.builder()
                .id(1L)
                .branchId(1L)
                .name("Customer Service")
                .description("Customer Service Department")
                .isActive(true)
                .createdAt(now)
                .createdBy(1L)
                .build();

        branchDepartment = BranchDepartment.builder()
                .id(1L)
                .branchId(1L)
                .name("Customer Service")
                .description("Customer Service Department")
                .isActive(true)
                .createdAt(now)
                .createdBy(1L)
                .build();
    }

    @Test
    void createBranchDepartment_ShouldCreateAndReturnBranchDepartment() {
        // Arrange
        when(branchDepartmentMapper.toEntity(branchDepartmentDTO)).thenReturn(branchDepartment);
        when(branchDepartmentRepository.save(branchDepartment)).thenReturn(Mono.just(branchDepartment));
        when(branchDepartmentMapper.toDTO(branchDepartment)).thenReturn(branchDepartmentDTO);

        // Act & Assert
        StepVerifier.create(branchDepartmentService.createBranchDepartment(branchDepartmentDTO))
                .expectNext(branchDepartmentDTO)
                .verifyComplete();

        verify(branchDepartmentMapper).toEntity(branchDepartmentDTO);
        verify(branchDepartmentRepository).save(branchDepartment);
        verify(branchDepartmentMapper).toDTO(branchDepartment);
    }

    @Test
    void updateBranchDepartment_WhenBranchDepartmentExists_ShouldUpdateAndReturnBranchDepartment() {
        // Arrange
        Long branchDepartmentId = 1L;
        when(branchDepartmentRepository.findById(branchDepartmentId)).thenReturn(Mono.just(branchDepartment));
        when(branchDepartmentMapper.toEntity(branchDepartmentDTO)).thenReturn(branchDepartment);
        when(branchDepartmentRepository.save(branchDepartment)).thenReturn(Mono.just(branchDepartment));
        when(branchDepartmentMapper.toDTO(branchDepartment)).thenReturn(branchDepartmentDTO);

        // Act & Assert
        StepVerifier.create(branchDepartmentService.updateBranchDepartment(branchDepartmentId, branchDepartmentDTO))
                .expectNext(branchDepartmentDTO)
                .verifyComplete();

        verify(branchDepartmentRepository).findById(branchDepartmentId);
        verify(branchDepartmentMapper).toEntity(branchDepartmentDTO);
        verify(branchDepartmentRepository).save(branchDepartment);
        verify(branchDepartmentMapper).toDTO(branchDepartment);
    }

    @Test
    void updateBranchDepartment_WhenBranchDepartmentDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchDepartmentId = 1L;
        when(branchDepartmentRepository.findById(branchDepartmentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchDepartmentService.updateBranchDepartment(branchDepartmentId, branchDepartmentDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch department not found with ID: " + branchDepartmentId))
                .verify();

        verify(branchDepartmentRepository).findById(branchDepartmentId);
    }

    @Test
    void deleteBranchDepartment_WhenBranchDepartmentExists_ShouldDeleteBranchDepartment() {
        // Arrange
        Long branchDepartmentId = 1L;
        when(branchDepartmentRepository.findById(branchDepartmentId)).thenReturn(Mono.just(branchDepartment));
        when(branchDepartmentRepository.deleteById(branchDepartmentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchDepartmentService.deleteBranchDepartment(branchDepartmentId))
                .verifyComplete();

        verify(branchDepartmentRepository).findById(branchDepartmentId);
        verify(branchDepartmentRepository).deleteById(branchDepartmentId);
    }

    @Test
    void deleteBranchDepartment_WhenBranchDepartmentDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchDepartmentId = 1L;
        when(branchDepartmentRepository.findById(branchDepartmentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchDepartmentService.deleteBranchDepartment(branchDepartmentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch department not found with ID: " + branchDepartmentId))
                .verify();

        verify(branchDepartmentRepository).findById(branchDepartmentId);
    }

    @Test
    void getBranchDepartmentById_WhenBranchDepartmentExists_ShouldReturnBranchDepartment() {
        // Arrange
        Long branchDepartmentId = 1L;
        when(branchDepartmentRepository.findById(branchDepartmentId)).thenReturn(Mono.just(branchDepartment));
        when(branchDepartmentMapper.toDTO(branchDepartment)).thenReturn(branchDepartmentDTO);

        // Act & Assert
        StepVerifier.create(branchDepartmentService.getBranchDepartmentById(branchDepartmentId))
                .expectNext(branchDepartmentDTO)
                .verifyComplete();

        verify(branchDepartmentRepository).findById(branchDepartmentId);
        verify(branchDepartmentMapper).toDTO(branchDepartment);
    }

    @Test
    void getBranchDepartmentById_WhenBranchDepartmentDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchDepartmentId = 1L;
        when(branchDepartmentRepository.findById(branchDepartmentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchDepartmentService.getBranchDepartmentById(branchDepartmentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch department not found with ID: " + branchDepartmentId))
                .verify();

        verify(branchDepartmentRepository).findById(branchDepartmentId);
    }
}