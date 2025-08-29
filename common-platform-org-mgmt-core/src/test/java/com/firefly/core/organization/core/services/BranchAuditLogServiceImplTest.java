package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BranchAuditLogMapper;
import com.firefly.core.organization.interfaces.dtos.BranchAuditLogDTO;
import com.firefly.core.organization.interfaces.enums.AuditAction;
import com.firefly.core.organization.models.entities.BranchAuditLog;
import com.firefly.core.organization.models.repositories.BranchAuditLogRepository;
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
public class BranchAuditLogServiceImplTest {

    @Mock
    private BranchAuditLogRepository branchAuditLogRepository;

    @Mock
    private BranchAuditLogMapper branchAuditLogMapper;

    @InjectMocks
    private BranchAuditLogServiceImpl branchAuditLogService;

    private BranchAuditLogDTO branchAuditLogDTO;
    private BranchAuditLog branchAuditLog;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();

        branchAuditLogDTO = BranchAuditLogDTO.builder()
                .id(1L)
                .branchId(1L)
                .action(AuditAction.CREATED)
                .entity("Branch")
                .entityId("1")
                .metadata("{\"name\":\"Main Branch\",\"code\":\"BR001\"}")
                .ipAddress("192.168.1.1")
                .userId(1L)
                .timestamp(now)
                .build();

        branchAuditLog = BranchAuditLog.builder()
                .id(1L)
                .branchId(1L)
                .action(AuditAction.CREATED)
                .entity("Branch")
                .entityId("1")
                .metadata("{\"name\":\"Main Branch\",\"code\":\"BR001\"}")
                .ipAddress("192.168.1.1")
                .userId(1L)
                .timestamp(now)
                .build();
    }


    @Test
    void createBranchAuditLog_ShouldCreateAndReturnBranchAuditLog() {
        // Arrange
        when(branchAuditLogMapper.toEntity(branchAuditLogDTO)).thenReturn(branchAuditLog);
        when(branchAuditLogRepository.save(branchAuditLog)).thenReturn(Mono.just(branchAuditLog));
        when(branchAuditLogMapper.toDTO(branchAuditLog)).thenReturn(branchAuditLogDTO);

        // Act & Assert
        StepVerifier.create(branchAuditLogService.createBranchAuditLog(branchAuditLogDTO))
                .expectNext(branchAuditLogDTO)
                .verifyComplete();

        verify(branchAuditLogMapper).toEntity(branchAuditLogDTO);
        verify(branchAuditLogRepository).save(branchAuditLog);
        verify(branchAuditLogMapper).toDTO(branchAuditLog);
    }

    @Test
    void updateBranchAuditLog_WhenBranchAuditLogExists_ShouldUpdateAndReturnBranchAuditLog() {
        // Arrange
        Long branchAuditLogId = 1L;
        when(branchAuditLogRepository.findById(branchAuditLogId)).thenReturn(Mono.just(branchAuditLog));
        when(branchAuditLogMapper.toEntity(branchAuditLogDTO)).thenReturn(branchAuditLog);
        when(branchAuditLogRepository.save(branchAuditLog)).thenReturn(Mono.just(branchAuditLog));
        when(branchAuditLogMapper.toDTO(branchAuditLog)).thenReturn(branchAuditLogDTO);

        // Act & Assert
        StepVerifier.create(branchAuditLogService.updateBranchAuditLog(branchAuditLogId, branchAuditLogDTO))
                .expectNext(branchAuditLogDTO)
                .verifyComplete();

        verify(branchAuditLogRepository).findById(branchAuditLogId);
        verify(branchAuditLogMapper).toEntity(branchAuditLogDTO);
        verify(branchAuditLogRepository).save(branchAuditLog);
        verify(branchAuditLogMapper).toDTO(branchAuditLog);
    }

    @Test
    void updateBranchAuditLog_WhenBranchAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchAuditLogId = 1L;
        when(branchAuditLogRepository.findById(branchAuditLogId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchAuditLogService.updateBranchAuditLog(branchAuditLogId, branchAuditLogDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch audit log not found with ID: " + branchAuditLogId))
                .verify();

        verify(branchAuditLogRepository).findById(branchAuditLogId);
    }

    @Test
    void deleteBranchAuditLog_WhenBranchAuditLogExists_ShouldDeleteBranchAuditLog() {
        // Arrange
        Long branchAuditLogId = 1L;
        when(branchAuditLogRepository.findById(branchAuditLogId)).thenReturn(Mono.just(branchAuditLog));
        when(branchAuditLogRepository.deleteById(branchAuditLogId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchAuditLogService.deleteBranchAuditLog(branchAuditLogId))
                .verifyComplete();

        verify(branchAuditLogRepository).findById(branchAuditLogId);
        verify(branchAuditLogRepository).deleteById(branchAuditLogId);
    }

    @Test
    void deleteBranchAuditLog_WhenBranchAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchAuditLogId = 1L;
        when(branchAuditLogRepository.findById(branchAuditLogId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchAuditLogService.deleteBranchAuditLog(branchAuditLogId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch audit log not found with ID: " + branchAuditLogId))
                .verify();

        verify(branchAuditLogRepository).findById(branchAuditLogId);
    }

    @Test
    void getBranchAuditLogById_WhenBranchAuditLogExists_ShouldReturnBranchAuditLog() {
        // Arrange
        Long branchAuditLogId = 1L;
        when(branchAuditLogRepository.findById(branchAuditLogId)).thenReturn(Mono.just(branchAuditLog));
        when(branchAuditLogMapper.toDTO(branchAuditLog)).thenReturn(branchAuditLogDTO);

        // Act & Assert
        StepVerifier.create(branchAuditLogService.getBranchAuditLogById(branchAuditLogId))
                .expectNext(branchAuditLogDTO)
                .verifyComplete();

        verify(branchAuditLogRepository).findById(branchAuditLogId);
        verify(branchAuditLogMapper).toDTO(branchAuditLog);
    }

    @Test
    void getBranchAuditLogById_WhenBranchAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        Long branchAuditLogId = 1L;
        when(branchAuditLogRepository.findById(branchAuditLogId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchAuditLogService.getBranchAuditLogById(branchAuditLogId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch audit log not found with ID: " + branchAuditLogId))
                .verify();

        verify(branchAuditLogRepository).findById(branchAuditLogId);
    }
}
