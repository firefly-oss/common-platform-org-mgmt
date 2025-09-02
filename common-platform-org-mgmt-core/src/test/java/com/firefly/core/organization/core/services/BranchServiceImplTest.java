package com.firefly.core.organization.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.mappers.BranchMapper;
import com.firefly.core.organization.interfaces.dtos.BankDTO;
import com.firefly.core.organization.interfaces.dtos.BranchDTO;
import com.firefly.core.organization.models.entities.Branch;
import com.firefly.core.organization.models.repositories.BranchRepository;
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
import java.util.Collections;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BranchServiceImplTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private BranchMapper branchMapper;

    @Mock
    private BankService bankService;

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
        UUID branchId = 1L;
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
        UUID branchId = 1L;
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
        UUID branchId = 1L;
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
        UUID branchId = 1L;
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
        UUID branchId = 1L;
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
        UUID branchId = 1L;
        when(branchRepository.findById(branchId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchService.getBranchById(branchId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch not found with ID: " + branchId))
                .verify();

        verify(branchRepository).findById(branchId);
    }

    // Skip filter tests as they require R2dbcEntityTemplate initialization
    // In a real application, these would be integration tests rather than unit tests

    @Test
    void filterBranchesForBank_WhenBankDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID bankId = 1L;
        FilterRequest<BranchDTO> filterRequest = new FilterRequest<>();

        when(bankService.getBankById(bankId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchService.filterBranchesForBank(bankId, filterRequest))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank not found with ID: " + bankId))
                .verify();

        verify(bankService).getBankById(bankId);
    }

    @Test
    void createBranchForBank_WhenBankExists_ShouldCreateAndReturnBranch() {
        // Arrange
        UUID bankId = 1L;
        BankDTO bankDTO = BankDTO.builder().id(bankId).build();

        when(bankService.getBankById(bankId)).thenReturn(Mono.just(bankDTO));
        when(branchMapper.toEntity(branchDTO)).thenReturn(branch);
        when(branchRepository.save(branch)).thenReturn(Mono.just(branch));
        when(branchMapper.toDTO(branch)).thenReturn(branchDTO);

        // Act & Assert
        StepVerifier.create(branchService.createBranchForBank(bankId, branchDTO))
                .expectNext(branchDTO)
                .verifyComplete();

        verify(bankService).getBankById(bankId);
        verify(branchMapper).toEntity(branchDTO);
        verify(branchRepository).save(branch);
        verify(branchMapper).toDTO(branch);
    }

    @Test
    void createBranchForBank_WhenBankDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID bankId = 1L;

        when(bankService.getBankById(bankId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchService.createBranchForBank(bankId, branchDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank not found with ID: " + bankId))
                .verify();

        verify(bankService).getBankById(bankId);
        verify(branchMapper, never()).toEntity(any());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateBranchForBank_WhenBankAndBranchExistAndBranchBelongsToBank_ShouldUpdateAndReturnBranch() {
        // Arrange
        UUID bankId = 1L;
        UUID branchId = 1L;
        BankDTO bankDTO = BankDTO.builder().id(bankId).build();

        branchDTO.setBankId(bankId);

        when(bankService.getBankById(bankId)).thenReturn(Mono.just(bankDTO));
        when(branchRepository.findById(branchId)).thenReturn(Mono.just(branch));
        when(branchMapper.toDTO(branch)).thenReturn(branchDTO);
        when(branchMapper.toEntity(branchDTO)).thenReturn(branch);
        when(branchRepository.save(branch)).thenReturn(Mono.just(branch));

        // Act & Assert
        StepVerifier.create(branchService.updateBranchForBank(bankId, branchId, branchDTO))
                .expectNext(branchDTO)
                .verifyComplete();

        verify(bankService).getBankById(bankId);
        verify(branchRepository, Mockito.times(2)).findById(branchId); // Called by getBranchById and updateBranch
        verify(branchMapper).toEntity(branchDTO);
        verify(branchRepository).save(branch);
        verify(branchMapper, Mockito.times(2)).toDTO(branch);
    }

    @Test
    void updateBranchForBank_WhenBankDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID bankId = 1L;
        UUID branchId = 1L;

        when(bankService.getBankById(bankId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchService.updateBranchForBank(bankId, branchId, branchDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank not found with ID: " + bankId))
                .verify();

        verify(bankService).getBankById(bankId);
        verify(branchRepository, never()).findById(anyLong());
    }

    @Test
    void updateBranchForBank_WhenBranchDoesNotBelongToBank_ShouldReturnError() {
        // Arrange
        UUID bankId = 1L;
        UUID branchId = 1L;
        BankDTO bankDTO = BankDTO.builder().id(bankId).build();

        // Branch belongs to a different bank
        branchDTO.setBankId(2L);

        when(bankService.getBankById(bankId)).thenReturn(Mono.just(bankDTO));
        when(branchRepository.findById(branchId)).thenReturn(Mono.just(branch));
        when(branchMapper.toDTO(branch)).thenReturn(branchDTO);

        // Act & Assert
        StepVerifier.create(branchService.updateBranchForBank(bankId, branchId, branchDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch not found for bank with ID: " + bankId))
                .verify();

        verify(bankService).getBankById(bankId);
        verify(branchRepository).findById(branchId);
        verify(branchMapper).toDTO(branch);
    }

    @Test
    void deleteBranchForBank_WhenBankAndBranchExistAndBranchBelongsToBank_ShouldDeleteBranch() {
        // Arrange
        UUID bankId = 1L;
        UUID branchId = 1L;
        BankDTO bankDTO = BankDTO.builder().id(bankId).build();

        branchDTO.setBankId(bankId);

        when(bankService.getBankById(bankId)).thenReturn(Mono.just(bankDTO));
        when(branchRepository.findById(branchId)).thenReturn(Mono.just(branch));
        when(branchMapper.toDTO(branch)).thenReturn(branchDTO);
        when(branchRepository.deleteById(branchId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchService.deleteBranchForBank(bankId, branchId))
                .verifyComplete();

        verify(bankService).getBankById(bankId);
        verify(branchRepository, Mockito.times(2)).findById(branchId); // Called by getBranchById and deleteBranch
        verify(branchMapper).toDTO(branch);
        verify(branchRepository).deleteById(branchId);
    }

    @Test
    void deleteBranchForBank_WhenBankDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID bankId = 1L;
        UUID branchId = 1L;

        when(bankService.getBankById(bankId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchService.deleteBranchForBank(bankId, branchId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank not found with ID: " + bankId))
                .verify();

        verify(bankService).getBankById(bankId);
        verify(branchRepository, never()).findById(anyLong());
    }

    @Test
    void deleteBranchForBank_WhenBranchDoesNotBelongToBank_ShouldReturnError() {
        // Arrange
        UUID bankId = 1L;
        UUID branchId = 1L;
        BankDTO bankDTO = BankDTO.builder().id(bankId).build();

        // Branch belongs to a different bank
        branchDTO.setBankId(2L);

        when(bankService.getBankById(bankId)).thenReturn(Mono.just(bankDTO));
        when(branchRepository.findById(branchId)).thenReturn(Mono.just(branch));
        when(branchMapper.toDTO(branch)).thenReturn(branchDTO);

        // Act & Assert
        StepVerifier.create(branchService.deleteBranchForBank(bankId, branchId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch not found for bank with ID: " + bankId))
                .verify();

        verify(bankService).getBankById(bankId);
        verify(branchRepository).findById(branchId);
        verify(branchMapper).toDTO(branch);
        verify(branchRepository, never()).deleteById(anyLong());
    }

    @Test
    void getBranchByIdForBank_WhenBankAndBranchExistAndBranchBelongsToBank_ShouldReturnBranch() {
        // Arrange
        UUID bankId = 1L;
        UUID branchId = 1L;
        BankDTO bankDTO = BankDTO.builder().id(bankId).build();

        branchDTO.setBankId(bankId);

        when(bankService.getBankById(bankId)).thenReturn(Mono.just(bankDTO));
        when(branchRepository.findById(branchId)).thenReturn(Mono.just(branch));
        when(branchMapper.toDTO(branch)).thenReturn(branchDTO);

        // Act & Assert
        StepVerifier.create(branchService.getBranchByIdForBank(bankId, branchId))
                .expectNext(branchDTO)
                .verifyComplete();

        verify(bankService).getBankById(bankId);
        verify(branchRepository).findById(branchId);
        verify(branchMapper).toDTO(branch);
    }

    @Test
    void getBranchByIdForBank_WhenBankDoesNotExist_ShouldReturnError() {
        // Arrange
        UUID bankId = 1L;
        UUID branchId = 1L;

        when(bankService.getBankById(bankId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchService.getBranchByIdForBank(bankId, branchId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank not found with ID: " + bankId))
                .verify();

        verify(bankService).getBankById(bankId);
        verify(branchRepository, never()).findById(anyLong());
    }

    @Test
    void getBranchByIdForBank_WhenBranchDoesNotBelongToBank_ShouldReturnError() {
        // Arrange
        UUID bankId = 1L;
        UUID branchId = 1L;
        BankDTO bankDTO = BankDTO.builder().id(bankId).build();

        // Branch belongs to a different bank
        branchDTO.setBankId(2L);

        when(bankService.getBankById(bankId)).thenReturn(Mono.just(bankDTO));
        when(branchRepository.findById(branchId)).thenReturn(Mono.just(branch));
        when(branchMapper.toDTO(branch)).thenReturn(branchDTO);

        // Act & Assert
        StepVerifier.create(branchService.getBranchByIdForBank(bankId, branchId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Branch not found for bank with ID: " + bankId))
                .verify();

        verify(bankService).getBankById(bankId);
        verify(branchRepository).findById(branchId);
        verify(branchMapper).toDTO(branch);
    }
}
