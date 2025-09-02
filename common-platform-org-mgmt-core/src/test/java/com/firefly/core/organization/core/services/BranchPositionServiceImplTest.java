package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BranchPositionMapper;
import com.firefly.core.organization.interfaces.dtos.BranchPositionDTO;
import com.firefly.core.organization.models.entities.BranchPosition;
import com.firefly.core.organization.models.repositories.BranchPositionRepository;
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
public class BranchPositionServiceImplTest {

    @Mock
    private BranchPositionRepository branchPositionRepository;

    @Mock
    private BranchPositionMapper branchPositionMapper;

    @InjectMocks
    private BranchPositionServiceImpl branchPositionService;

    private BranchPositionDTO branchPositionDTO;
    private BranchPosition branchPosition;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        
        UUID testId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID testDepartmentId = UUID.fromString("223e4567-e89b-12d3-a456-426614174000");
        UUID testUserId = UUID.fromString("323e4567-e89b-12d3-a456-426614174000");

        branchPositionDTO = BranchPositionDTO.builder()
                .id(testId)
                .departmentId(testDepartmentId)
                .title("Teller")
                .description("Bank Teller Position")
                .isActive(true)
                .createdAt(now)
                .createdBy(testUserId)
                .build();

        branchPosition = BranchPosition.builder()
                .id(testId)
                .departmentId(testDepartmentId)
                .title("Teller")
                .description("Bank Teller Position")
                .isActive(true)
                .createdAt(now)
                .createdBy(testUserId)
                .build();
    }

    @Test
    void createBranchPosition_ShouldCreateAndReturnBranchPosition() {
        // Arrange
        when(branchPositionMapper.toEntity(branchPositionDTO)).thenReturn(branchPosition);
        when(branchPositionRepository.save(branchPosition)).thenReturn(Mono.just(branchPosition));
        when(branchPositionMapper.toDTO(branchPosition)).thenReturn(branchPositionDTO);

        // Act & Assert
        StepVerifier.create(branchPositionService.createBranchPosition(branchPositionDTO))
                .expectNext(branchPositionDTO)
                .verifyComplete();

        verify(branchPositionMapper).toEntity(branchPositionDTO);
        verify(branchPositionRepository).save(branchPosition);
        verify(branchPositionMapper).toDTO(branchPosition);
    }

    @Test
    void updateBranchPosition_WhenBranchPositionExists_ShouldUpdateAndReturnBranchPosition() {
        // Arrange
        UUID branchPositionId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(branchPositionRepository.findById(branchPositionId)).thenReturn(Mono.just(branchPosition));
        when(branchPositionMapper.toEntity(branchPositionDTO)).thenReturn(branchPosition);
        when(branchPositionRepository.save(branchPosition)).thenReturn(Mono.just(branchPosition));
        when(branchPositionMapper.toDTO(branchPosition)).thenReturn(branchPositionDTO);

        // Act & Assert
        StepVerifier.create(branchPositionService.updateBranchPosition(branchPositionId, branchPositionDTO))
                .expectNext(branchPositionDTO)
                .verifyComplete();

        verify(branchPositionRepository).findById(branchPositionId);
        verify(branchPositionMapper).toEntity(branchPositionDTO);
        verify(branchPositionRepository).save(branchPosition);
        verify(branchPositionMapper).toDTO(branchPosition);
    }

    @Test
    void updateBranchPosition_WhenBranchPositionDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID branchPositionId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(branchPositionRepository.findById(branchPositionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchPositionService.updateBranchPosition(branchPositionId, branchPositionDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch position not found with ID: " + branchPositionId))
                .verify();

        verify(branchPositionRepository).findById(branchPositionId);
    }

    @Test
    void deleteBranchPosition_WhenBranchPositionExists_ShouldDeleteBranchPosition() {
        // Arrange
        UUID branchPositionId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(branchPositionRepository.findById(branchPositionId)).thenReturn(Mono.just(branchPosition));
        when(branchPositionRepository.deleteById(branchPositionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchPositionService.deleteBranchPosition(branchPositionId))
                .verifyComplete();

        verify(branchPositionRepository).findById(branchPositionId);
        verify(branchPositionRepository).deleteById(branchPositionId);
    }

    @Test
    void deleteBranchPosition_WhenBranchPositionDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID branchPositionId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(branchPositionRepository.findById(branchPositionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchPositionService.deleteBranchPosition(branchPositionId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch position not found with ID: " + branchPositionId))
                .verify();

        verify(branchPositionRepository).findById(branchPositionId);
    }

    @Test
    void getBranchPositionById_WhenBranchPositionExists_ShouldReturnBranchPosition() {
        // Arrange
        UUID branchPositionId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(branchPositionRepository.findById(branchPositionId)).thenReturn(Mono.just(branchPosition));
        when(branchPositionMapper.toDTO(branchPosition)).thenReturn(branchPositionDTO);

        // Act & Assert
        StepVerifier.create(branchPositionService.getBranchPositionById(branchPositionId))
                .expectNext(branchPositionDTO)
                .verifyComplete();

        verify(branchPositionRepository).findById(branchPositionId);
        verify(branchPositionMapper).toDTO(branchPosition);
    }

    @Test
    void getBranchPositionById_WhenBranchPositionDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID branchPositionId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(branchPositionRepository.findById(branchPositionId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchPositionService.getBranchPositionById(branchPositionId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch position not found with ID: " + branchPositionId))
                .verify();

        verify(branchPositionRepository).findById(branchPositionId);
    }
}