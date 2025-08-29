package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.WorkingCalendarMapper;
import com.firefly.core.organization.interfaces.dtos.WorkingCalendarDTO;
import com.firefly.core.organization.models.entities.WorkingCalendar;
import com.firefly.core.organization.models.repositories.WorkingCalendarRepository;
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
public class WorkingCalendarServiceImplTest {

    @Mock
    private WorkingCalendarRepository workingCalendarRepository;

    @Mock
    private WorkingCalendarMapper workingCalendarMapper;

    @InjectMocks
    private WorkingCalendarServiceImpl workingCalendarService;

    private WorkingCalendarDTO workingCalendarDTO;
    private WorkingCalendar workingCalendar;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        
        workingCalendarDTO = WorkingCalendarDTO.builder()
                .id(1L)
                .bankId(1L)
                .name("Standard Working Calendar")
                .description("Standard 9-5 Working Calendar")
                .isDefault(true)
                .timeZoneId(1L)
                .createdAt(now)
                .createdBy(1L)
                .build();

        workingCalendar = WorkingCalendar.builder()
                .id(1L)
                .bankId(1L)
                .name("Standard Working Calendar")
                .description("Standard 9-5 Working Calendar")
                .isDefault(true)
                .timeZoneId(1L)
                .createdAt(now)
                .createdBy(1L)
                .build();
    }

    @Test
    void createWorkingCalendar_ShouldCreateAndReturnWorkingCalendar() {
        // Arrange
        when(workingCalendarMapper.toEntity(workingCalendarDTO)).thenReturn(workingCalendar);
        when(workingCalendarRepository.save(workingCalendar)).thenReturn(Mono.just(workingCalendar));
        when(workingCalendarMapper.toDTO(workingCalendar)).thenReturn(workingCalendarDTO);

        // Act & Assert
        StepVerifier.create(workingCalendarService.createWorkingCalendar(workingCalendarDTO))
                .expectNext(workingCalendarDTO)
                .verifyComplete();

        verify(workingCalendarMapper).toEntity(workingCalendarDTO);
        verify(workingCalendarRepository).save(workingCalendar);
        verify(workingCalendarMapper).toDTO(workingCalendar);
    }

    @Test
    void updateWorkingCalendar_WhenWorkingCalendarExists_ShouldUpdateAndReturnWorkingCalendar() {
        // Arrange
        Long workingCalendarId = 1L;
        when(workingCalendarRepository.findById(workingCalendarId)).thenReturn(Mono.just(workingCalendar));
        when(workingCalendarMapper.toEntity(workingCalendarDTO)).thenReturn(workingCalendar);
        when(workingCalendarRepository.save(workingCalendar)).thenReturn(Mono.just(workingCalendar));
        when(workingCalendarMapper.toDTO(workingCalendar)).thenReturn(workingCalendarDTO);

        // Act & Assert
        StepVerifier.create(workingCalendarService.updateWorkingCalendar(workingCalendarId, workingCalendarDTO))
                .expectNext(workingCalendarDTO)
                .verifyComplete();

        verify(workingCalendarRepository).findById(workingCalendarId);
        verify(workingCalendarMapper).toEntity(workingCalendarDTO);
        verify(workingCalendarRepository).save(workingCalendar);
        verify(workingCalendarMapper).toDTO(workingCalendar);
    }

    @Test
    void updateWorkingCalendar_WhenWorkingCalendarDoesNotExist_ShouldReturnError() {
        // Arrange
        Long workingCalendarId = 1L;
        when(workingCalendarRepository.findById(workingCalendarId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(workingCalendarService.updateWorkingCalendar(workingCalendarId, workingCalendarDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Working calendar not found with ID: " + workingCalendarId))
                .verify();

        verify(workingCalendarRepository).findById(workingCalendarId);
    }

    @Test
    void deleteWorkingCalendar_WhenWorkingCalendarExists_ShouldDeleteWorkingCalendar() {
        // Arrange
        Long workingCalendarId = 1L;
        when(workingCalendarRepository.findById(workingCalendarId)).thenReturn(Mono.just(workingCalendar));
        when(workingCalendarRepository.deleteById(workingCalendarId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(workingCalendarService.deleteWorkingCalendar(workingCalendarId))
                .verifyComplete();

        verify(workingCalendarRepository).findById(workingCalendarId);
        verify(workingCalendarRepository).deleteById(workingCalendarId);
    }

    @Test
    void deleteWorkingCalendar_WhenWorkingCalendarDoesNotExist_ShouldReturnError() {
        // Arrange
        Long workingCalendarId = 1L;
        when(workingCalendarRepository.findById(workingCalendarId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(workingCalendarService.deleteWorkingCalendar(workingCalendarId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Working calendar not found with ID: " + workingCalendarId))
                .verify();

        verify(workingCalendarRepository).findById(workingCalendarId);
    }

    @Test
    void getWorkingCalendarById_WhenWorkingCalendarExists_ShouldReturnWorkingCalendar() {
        // Arrange
        Long workingCalendarId = 1L;
        when(workingCalendarRepository.findById(workingCalendarId)).thenReturn(Mono.just(workingCalendar));
        when(workingCalendarMapper.toDTO(workingCalendar)).thenReturn(workingCalendarDTO);

        // Act & Assert
        StepVerifier.create(workingCalendarService.getWorkingCalendarById(workingCalendarId))
                .expectNext(workingCalendarDTO)
                .verifyComplete();

        verify(workingCalendarRepository).findById(workingCalendarId);
        verify(workingCalendarMapper).toDTO(workingCalendar);
    }

    @Test
    void getWorkingCalendarById_WhenWorkingCalendarDoesNotExist_ShouldReturnError() {
        // Arrange
        Long workingCalendarId = 1L;
        when(workingCalendarRepository.findById(workingCalendarId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(workingCalendarService.getWorkingCalendarById(workingCalendarId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Working calendar not found with ID: " + workingCalendarId))
                .verify();

        verify(workingCalendarRepository).findById(workingCalendarId);
    }
}