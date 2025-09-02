package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.CalendarAssignmentMapper;
import com.firefly.core.organization.interfaces.dtos.CalendarAssignmentDTO;
import com.firefly.core.organization.models.entities.CalendarAssignment;
import com.firefly.core.organization.models.repositories.CalendarAssignmentRepository;
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
public class CalendarAssignmentServiceImplTest {

    @Mock
    private CalendarAssignmentRepository calendarAssignmentRepository;

    @Mock
    private CalendarAssignmentMapper calendarAssignmentMapper;

    @InjectMocks
    private CalendarAssignmentServiceImpl calendarAssignmentService;

    private CalendarAssignmentDTO calendarAssignmentDTO;
    private CalendarAssignment calendarAssignment;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        
        calendarAssignmentDTO = CalendarAssignmentDTO.builder()
                .id(1L)
                .calendarId(1L)
                .branchId(1L)
                .departmentId(1L)
                .positionId(null) // Optional field
                .effectiveFrom(now)
                .effectiveTo(now.plusYears(1))
                .isActive(true)
                .createdAt(now)
                .createdBy(1L)
                .build();

        calendarAssignment = CalendarAssignment.builder()
                .id(1L)
                .calendarId(1L)
                .branchId(1L)
                .departmentId(1L)
                .positionId(null) // Optional field
                .effectiveFrom(now)
                .effectiveTo(now.plusYears(1))
                .isActive(true)
                .createdAt(now)
                .createdBy(1L)
                .build();
    }

    @Test
    void createCalendarAssignment_ShouldCreateAndReturnCalendarAssignment() {
        // Arrange
        when(calendarAssignmentMapper.toEntity(calendarAssignmentDTO)).thenReturn(calendarAssignment);
        when(calendarAssignmentRepository.save(calendarAssignment)).thenReturn(Mono.just(calendarAssignment));
        when(calendarAssignmentMapper.toDTO(calendarAssignment)).thenReturn(calendarAssignmentDTO);

        // Act & Assert
        StepVerifier.create(calendarAssignmentService.createCalendarAssignment(calendarAssignmentDTO))
                .expectNext(calendarAssignmentDTO)
                .verifyComplete();

        verify(calendarAssignmentMapper).toEntity(calendarAssignmentDTO);
        verify(calendarAssignmentRepository).save(calendarAssignment);
        verify(calendarAssignmentMapper).toDTO(calendarAssignment);
    }

    @Test
    void updateCalendarAssignment_WhenCalendarAssignmentExists_ShouldUpdateAndReturnCalendarAssignment() {
        // Arrange
        UUID calendarAssignmentId = 1L;
        when(calendarAssignmentRepository.findById(calendarAssignmentId)).thenReturn(Mono.just(calendarAssignment));
        when(calendarAssignmentMapper.toEntity(calendarAssignmentDTO)).thenReturn(calendarAssignment);
        when(calendarAssignmentRepository.save(calendarAssignment)).thenReturn(Mono.just(calendarAssignment));
        when(calendarAssignmentMapper.toDTO(calendarAssignment)).thenReturn(calendarAssignmentDTO);

        // Act & Assert
        StepVerifier.create(calendarAssignmentService.updateCalendarAssignment(calendarAssignmentId, calendarAssignmentDTO))
                .expectNext(calendarAssignmentDTO)
                .verifyComplete();

        verify(calendarAssignmentRepository).findById(calendarAssignmentId);
        verify(calendarAssignmentMapper).toEntity(calendarAssignmentDTO);
        verify(calendarAssignmentRepository).save(calendarAssignment);
        verify(calendarAssignmentMapper).toDTO(calendarAssignment);
    }

    @Test
    void updateCalendarAssignment_WhenCalendarAssignmentDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID calendarAssignmentId = 1L;
        when(calendarAssignmentRepository.findById(calendarAssignmentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(calendarAssignmentService.updateCalendarAssignment(calendarAssignmentId, calendarAssignmentDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Calendar assignment not found with ID: " + calendarAssignmentId))
                .verify();

        verify(calendarAssignmentRepository).findById(calendarAssignmentId);
    }

    @Test
    void deleteCalendarAssignment_WhenCalendarAssignmentExists_ShouldDeleteCalendarAssignment() {
        // Arrange
        UUID calendarAssignmentId = 1L;
        when(calendarAssignmentRepository.findById(calendarAssignmentId)).thenReturn(Mono.just(calendarAssignment));
        when(calendarAssignmentRepository.deleteById(calendarAssignmentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(calendarAssignmentService.deleteCalendarAssignment(calendarAssignmentId))
                .verifyComplete();

        verify(calendarAssignmentRepository).findById(calendarAssignmentId);
        verify(calendarAssignmentRepository).deleteById(calendarAssignmentId);
    }

    @Test
    void deleteCalendarAssignment_WhenCalendarAssignmentDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID calendarAssignmentId = 1L;
        when(calendarAssignmentRepository.findById(calendarAssignmentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(calendarAssignmentService.deleteCalendarAssignment(calendarAssignmentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Calendar assignment not found with ID: " + calendarAssignmentId))
                .verify();

        verify(calendarAssignmentRepository).findById(calendarAssignmentId);
    }

    @Test
    void getCalendarAssignmentById_WhenCalendarAssignmentExists_ShouldReturnCalendarAssignment() {
        // Arrange
        UUID calendarAssignmentId = 1L;
        when(calendarAssignmentRepository.findById(calendarAssignmentId)).thenReturn(Mono.just(calendarAssignment));
        when(calendarAssignmentMapper.toDTO(calendarAssignment)).thenReturn(calendarAssignmentDTO);

        // Act & Assert
        StepVerifier.create(calendarAssignmentService.getCalendarAssignmentById(calendarAssignmentId))
                .expectNext(calendarAssignmentDTO)
                .verifyComplete();

        verify(calendarAssignmentRepository).findById(calendarAssignmentId);
        verify(calendarAssignmentMapper).toDTO(calendarAssignment);
    }

    @Test
    void getCalendarAssignmentById_WhenCalendarAssignmentDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID calendarAssignmentId = 1L;
        when(calendarAssignmentRepository.findById(calendarAssignmentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(calendarAssignmentService.getCalendarAssignmentById(calendarAssignmentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Calendar assignment not found with ID: " + calendarAssignmentId))
                .verify();

        verify(calendarAssignmentRepository).findById(calendarAssignmentId);
    }
}