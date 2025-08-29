package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BankAuditLogMapper;
import com.firefly.core.organization.interfaces.dtos.BankAuditLogDTO;
import com.firefly.core.organization.interfaces.enums.AuditAction;
import com.firefly.core.organization.models.entities.BankAuditLog;
import com.firefly.core.organization.models.repositories.BankAuditLogRepository;
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
public class BankAuditLogServiceImplTest {

    @Mock
    private BankAuditLogRepository bankAuditLogRepository;

    @Mock
    private BankAuditLogMapper bankAuditLogMapper;

    @InjectMocks
    private BankAuditLogServiceImpl bankAuditLogService;

    private BankAuditLogDTO bankAuditLogDTO;
    private BankAuditLog bankAuditLog;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();

        bankAuditLogDTO = BankAuditLogDTO.builder()
                .id(1L)
                .bankId(1L)
                .action(AuditAction.CREATED)
                .entity("Bank")
                .entityId("1")
                .metadata("{\"name\":\"Test Bank\",\"code\":\"TEST\"}")
                .ipAddress("192.168.1.1")
                .userId(1L)
                .timestamp(now)
                .build();

        bankAuditLog = BankAuditLog.builder()
                .id(1L)
                .bankId(1L)
                .action(AuditAction.CREATED)
                .entity("Bank")
                .entityId("1")
                .metadata("{\"name\":\"Test Bank\",\"code\":\"TEST\"}")
                .ipAddress("192.168.1.1")
                .userId(1L)
                .timestamp(now)
                .build();
    }


    @Test
    void createBankAuditLog_ShouldCreateAndReturnBankAuditLog() {
        // Arrange
        when(bankAuditLogMapper.toEntity(bankAuditLogDTO)).thenReturn(bankAuditLog);
        when(bankAuditLogRepository.save(bankAuditLog)).thenReturn(Mono.just(bankAuditLog));
        when(bankAuditLogMapper.toDTO(bankAuditLog)).thenReturn(bankAuditLogDTO);

        // Act & Assert
        StepVerifier.create(bankAuditLogService.createBankAuditLog(bankAuditLogDTO))
                .expectNext(bankAuditLogDTO)
                .verifyComplete();

        verify(bankAuditLogMapper).toEntity(bankAuditLogDTO);
        verify(bankAuditLogRepository).save(bankAuditLog);
        verify(bankAuditLogMapper).toDTO(bankAuditLog);
    }

    @Test
    void updateBankAuditLog_WhenBankAuditLogExists_ShouldUpdateAndReturnBankAuditLog() {
        // Arrange
        Long bankAuditLogId = 1L;
        when(bankAuditLogRepository.findById(bankAuditLogId)).thenReturn(Mono.just(bankAuditLog));
        when(bankAuditLogMapper.toEntity(bankAuditLogDTO)).thenReturn(bankAuditLog);
        when(bankAuditLogRepository.save(bankAuditLog)).thenReturn(Mono.just(bankAuditLog));
        when(bankAuditLogMapper.toDTO(bankAuditLog)).thenReturn(bankAuditLogDTO);

        // Act & Assert
        StepVerifier.create(bankAuditLogService.updateBankAuditLog(bankAuditLogId, bankAuditLogDTO))
                .expectNext(bankAuditLogDTO)
                .verifyComplete();

        verify(bankAuditLogRepository).findById(bankAuditLogId);
        verify(bankAuditLogMapper).toEntity(bankAuditLogDTO);
        verify(bankAuditLogRepository).save(bankAuditLog);
        verify(bankAuditLogMapper).toDTO(bankAuditLog);
    }

    @Test
    void updateBankAuditLog_WhenBankAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankAuditLogId = 1L;
        when(bankAuditLogRepository.findById(bankAuditLogId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankAuditLogService.updateBankAuditLog(bankAuditLogId, bankAuditLogDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank audit log not found with ID: " + bankAuditLogId))
                .verify();

        verify(bankAuditLogRepository).findById(bankAuditLogId);
    }

    @Test
    void deleteBankAuditLog_WhenBankAuditLogExists_ShouldDeleteBankAuditLog() {
        // Arrange
        Long bankAuditLogId = 1L;
        when(bankAuditLogRepository.findById(bankAuditLogId)).thenReturn(Mono.just(bankAuditLog));
        when(bankAuditLogRepository.deleteById(bankAuditLogId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankAuditLogService.deleteBankAuditLog(bankAuditLogId))
                .verifyComplete();

        verify(bankAuditLogRepository).findById(bankAuditLogId);
        verify(bankAuditLogRepository).deleteById(bankAuditLogId);
    }

    @Test
    void deleteBankAuditLog_WhenBankAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankAuditLogId = 1L;
        when(bankAuditLogRepository.findById(bankAuditLogId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankAuditLogService.deleteBankAuditLog(bankAuditLogId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank audit log not found with ID: " + bankAuditLogId))
                .verify();

        verify(bankAuditLogRepository).findById(bankAuditLogId);
    }

    @Test
    void getBankAuditLogById_WhenBankAuditLogExists_ShouldReturnBankAuditLog() {
        // Arrange
        Long bankAuditLogId = 1L;
        when(bankAuditLogRepository.findById(bankAuditLogId)).thenReturn(Mono.just(bankAuditLog));
        when(bankAuditLogMapper.toDTO(bankAuditLog)).thenReturn(bankAuditLogDTO);

        // Act & Assert
        StepVerifier.create(bankAuditLogService.getBankAuditLogById(bankAuditLogId))
                .expectNext(bankAuditLogDTO)
                .verifyComplete();

        verify(bankAuditLogRepository).findById(bankAuditLogId);
        verify(bankAuditLogMapper).toDTO(bankAuditLog);
    }

    @Test
    void getBankAuditLogById_WhenBankAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankAuditLogId = 1L;
        when(bankAuditLogRepository.findById(bankAuditLogId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankAuditLogService.getBankAuditLogById(bankAuditLogId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank audit log not found with ID: " + bankAuditLogId))
                .verify();

        verify(bankAuditLogRepository).findById(bankAuditLogId);
    }
}
