package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.BranchHoursMapper;
import com.catalis.core.organization.interfaces.dtos.BranchHoursDTO;
import com.catalis.core.organization.interfaces.enums.DayOfWeek;
import com.catalis.core.organization.models.entities.BranchHours;
import com.catalis.core.organization.models.repositories.BranchHoursRepository;
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
import java.time.LocalTime;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BranchHoursServiceImplTest {

    @Mock
    private BranchHoursRepository branchHoursRepository;

    @Mock
    private BranchHoursMapper branchHoursMapper;

    @InjectMocks
    private BranchHoursServiceImpl branchHoursService;

    private BranchHoursDTO branchHoursDTO;
    private BranchHours branchHours;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        LocalTime openTime = LocalTime.of(9, 0); // 9:00 AM
        LocalTime closeTime = LocalTime.of(17, 0); // 5:00 PM
        
        branchHoursDTO = BranchHoursDTO.builder()
                .id(1L)
                .branchId(1L)
                .dayOfWeek(DayOfWeek.MONDAY)
                .openTime(openTime)
                .closeTime(closeTime)
                .isClosed(false)
                .createdAt(now)
                .createdBy(1L)
                .build();

        branchHours = BranchHours.builder()
                .id(1L)
                .branchId(1L)
                .dayOfWeek(DayOfWeek.MONDAY)
                .openTime(openTime)
                .closeTime(closeTime)
                .isClosed(false)
                .createdAt(now)
                .createdBy(1L)
                .build();
    }

    @Test
    void createBranchHours_ShouldCreateAndReturnBranchHours() {
        // Arrange
        when(branchHoursMapper.toEntity(branchHoursDTO)).thenReturn(branchHours);
        when(branchHoursRepository.save(branchHours)).thenReturn(Mono.just(branchHours));
        when(branchHoursMapper.toDTO(branchHours)).thenReturn(branchHoursDTO);

        // Act & Assert
        StepVerifier.create(branchHoursService.createBranchHours(branchHoursDTO))
                .expectNext(branchHoursDTO)
                .verifyComplete();

        verify(branchHoursMapper).toEntity(branchHoursDTO);
        verify(branchHoursRepository).save(branchHours);
        verify(branchHoursMapper).toDTO(branchHours);
    }

    @Test
    void updateBranchHours_WhenBranchHoursExists_ShouldUpdateAndReturnBranchHours() {
        // Arrange
        Long branchHoursId = 1L;
        when(branchHoursRepository.findById(branchHoursId)).thenReturn(Mono.just(branchHours));
        when(branchHoursMapper.toEntity(branchHoursDTO)).thenReturn(branchHours);
        when(branchHoursRepository.save(branchHours)).thenReturn(Mono.just(branchHours));
        when(branchHoursMapper.toDTO(branchHours)).thenReturn(branchHoursDTO);

        // Act & Assert
        StepVerifier.create(branchHoursService.updateBranchHours(branchHoursId, branchHoursDTO))
                .expectNext(branchHoursDTO)
                .verifyComplete();

        verify(branchHoursRepository).findById(branchHoursId);
        verify(branchHoursMapper).toEntity(branchHoursDTO);
        verify(branchHoursRepository).save(branchHours);
        verify(branchHoursMapper).toDTO(branchHours);
    }

    @Test
    void updateBranchHours_WhenBranchHoursDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchHoursId = 1L;
        when(branchHoursRepository.findById(branchHoursId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchHoursService.updateBranchHours(branchHoursId, branchHoursDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch hours not found with ID: " + branchHoursId))
                .verify();

        verify(branchHoursRepository).findById(branchHoursId);
    }

    @Test
    void deleteBranchHours_WhenBranchHoursExists_ShouldDeleteBranchHours() {
        // Arrange
        Long branchHoursId = 1L;
        when(branchHoursRepository.findById(branchHoursId)).thenReturn(Mono.just(branchHours));
        when(branchHoursRepository.deleteById(branchHoursId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchHoursService.deleteBranchHours(branchHoursId))
                .verifyComplete();

        verify(branchHoursRepository).findById(branchHoursId);
        verify(branchHoursRepository).deleteById(branchHoursId);
    }

    @Test
    void deleteBranchHours_WhenBranchHoursDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchHoursId = 1L;
        when(branchHoursRepository.findById(branchHoursId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchHoursService.deleteBranchHours(branchHoursId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch hours not found with ID: " + branchHoursId))
                .verify();

        verify(branchHoursRepository).findById(branchHoursId);
    }

    @Test
    void getBranchHoursById_WhenBranchHoursExists_ShouldReturnBranchHours() {
        // Arrange
        Long branchHoursId = 1L;
        when(branchHoursRepository.findById(branchHoursId)).thenReturn(Mono.just(branchHours));
        when(branchHoursMapper.toDTO(branchHours)).thenReturn(branchHoursDTO);

        // Act & Assert
        StepVerifier.create(branchHoursService.getBranchHoursById(branchHoursId))
                .expectNext(branchHoursDTO)
                .verifyComplete();

        verify(branchHoursRepository).findById(branchHoursId);
        verify(branchHoursMapper).toDTO(branchHours);
    }

    @Test
    void getBranchHoursById_WhenBranchHoursDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchHoursId = 1L;
        when(branchHoursRepository.findById(branchHoursId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchHoursService.getBranchHoursById(branchHoursId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch hours not found with ID: " + branchHoursId))
                .verify();

        verify(branchHoursRepository).findById(branchHoursId);
    }
}