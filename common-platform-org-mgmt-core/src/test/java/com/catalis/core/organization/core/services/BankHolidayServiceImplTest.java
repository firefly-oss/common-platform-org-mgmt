package com.catalis.core.organization.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.organization.core.mappers.BankHolidayMapper;
import com.catalis.core.organization.interfaces.dtos.BankHolidayDTO;
import com.catalis.core.organization.models.entities.BankHoliday;
import com.catalis.core.organization.models.repositories.BankHolidayRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankHolidayServiceImplTest {

    @Mock
    private BankHolidayRepository bankHolidayRepository;

    @Mock
    private BankHolidayMapper bankHolidayMapper;

    @InjectMocks
    private BankHolidayServiceImpl bankHolidayService;

    private BankHolidayDTO bankHolidayDTO;
    private BankHoliday bankHoliday;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDate holidayDate = LocalDate.of(2023, 12, 25);
        LocalDateTime now = LocalDateTime.now();

        bankHolidayDTO = BankHolidayDTO.builder()
                .id(1L)
                .bankId(1L)
                .name("Christmas Day")
                .date(holidayDate)
                .isRecurring(true)
                .description("Christmas Day Holiday")
                .createdAt(now)
                .build();

        bankHoliday = BankHoliday.builder()
                .id(1L)
                .bankId(1L)
                .name("Christmas Day")
                .date(holidayDate)
                .isRecurring(true)
                .description("Christmas Day Holiday")
                .createdAt(now)
                .build();
    }


    @Test
    void createBankHoliday_ShouldCreateAndReturnBankHoliday() {
        // Arrange
        when(bankHolidayMapper.toEntity(bankHolidayDTO)).thenReturn(bankHoliday);
        when(bankHolidayRepository.save(bankHoliday)).thenReturn(Mono.just(bankHoliday));
        when(bankHolidayMapper.toDTO(bankHoliday)).thenReturn(bankHolidayDTO);

        // Act & Assert
        StepVerifier.create(bankHolidayService.createBankHoliday(bankHolidayDTO))
                .expectNext(bankHolidayDTO)
                .verifyComplete();

        verify(bankHolidayMapper).toEntity(bankHolidayDTO);
        verify(bankHolidayRepository).save(bankHoliday);
        verify(bankHolidayMapper).toDTO(bankHoliday);
    }

    @Test
    void updateBankHoliday_WhenBankHolidayExists_ShouldUpdateAndReturnBankHoliday() {
        // Arrange
        Long bankHolidayId = 1L;
        when(bankHolidayRepository.findById(bankHolidayId)).thenReturn(Mono.just(bankHoliday));
        when(bankHolidayMapper.toEntity(bankHolidayDTO)).thenReturn(bankHoliday);
        when(bankHolidayRepository.save(bankHoliday)).thenReturn(Mono.just(bankHoliday));
        when(bankHolidayMapper.toDTO(bankHoliday)).thenReturn(bankHolidayDTO);

        // Act & Assert
        StepVerifier.create(bankHolidayService.updateBankHoliday(bankHolidayId, bankHolidayDTO))
                .expectNext(bankHolidayDTO)
                .verifyComplete();

        verify(bankHolidayRepository).findById(bankHolidayId);
        verify(bankHolidayMapper).toEntity(bankHolidayDTO);
        verify(bankHolidayRepository).save(bankHoliday);
        verify(bankHolidayMapper).toDTO(bankHoliday);
    }

    @Test
    void updateBankHoliday_WhenBankHolidayDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankHolidayId = 1L;
        when(bankHolidayRepository.findById(bankHolidayId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankHolidayService.updateBankHoliday(bankHolidayId, bankHolidayDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank holiday not found with ID: " + bankHolidayId))
                .verify();

        verify(bankHolidayRepository).findById(bankHolidayId);
    }

    @Test
    void deleteBankHoliday_WhenBankHolidayExists_ShouldDeleteBankHoliday() {
        // Arrange
        Long bankHolidayId = 1L;
        when(bankHolidayRepository.findById(bankHolidayId)).thenReturn(Mono.just(bankHoliday));
        when(bankHolidayRepository.deleteById(bankHolidayId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankHolidayService.deleteBankHoliday(bankHolidayId))
                .verifyComplete();

        verify(bankHolidayRepository).findById(bankHolidayId);
        verify(bankHolidayRepository).deleteById(bankHolidayId);
    }

    @Test
    void deleteBankHoliday_WhenBankHolidayDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankHolidayId = 1L;
        when(bankHolidayRepository.findById(bankHolidayId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankHolidayService.deleteBankHoliday(bankHolidayId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank holiday not found with ID: " + bankHolidayId))
                .verify();

        verify(bankHolidayRepository).findById(bankHolidayId);
    }

    @Test
    void getBankHolidayById_WhenBankHolidayExists_ShouldReturnBankHoliday() {
        // Arrange
        Long bankHolidayId = 1L;
        when(bankHolidayRepository.findById(bankHolidayId)).thenReturn(Mono.just(bankHoliday));
        when(bankHolidayMapper.toDTO(bankHoliday)).thenReturn(bankHolidayDTO);

        // Act & Assert
        StepVerifier.create(bankHolidayService.getBankHolidayById(bankHolidayId))
                .expectNext(bankHolidayDTO)
                .verifyComplete();

        verify(bankHolidayRepository).findById(bankHolidayId);
        verify(bankHolidayMapper).toDTO(bankHoliday);
    }

    @Test
    void getBankHolidayById_WhenBankHolidayDoesNotExist_ShouldReturnError() {
        // Arrange
        Long bankHolidayId = 1L;
        when(bankHolidayRepository.findById(bankHolidayId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(bankHolidayService.getBankHolidayById(bankHolidayId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank holiday not found with ID: " + bankHolidayId))
                .verify();

        verify(bankHolidayRepository).findById(bankHolidayId);
    }
}
