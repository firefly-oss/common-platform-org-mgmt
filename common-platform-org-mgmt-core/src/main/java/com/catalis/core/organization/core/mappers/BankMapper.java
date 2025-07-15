package com.catalis.core.organization.core.mappers;

import com.catalis.core.organization.interfaces.dtos.BankDTO;
import com.catalis.core.organization.models.entities.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between Bank entity and BankDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankMapper {

    /**
     * Converts a Bank entity to a BankDTO.
     *
     * @param entity the Bank entity to convert
     * @return the corresponding BankDTO
     */
    BankDTO toDTO(Bank entity);

    /**
     * Converts a BankDTO to a Bank entity.
     *
     * @param dto the BankDTO to convert
     * @return the corresponding Bank entity
     */
    Bank toEntity(BankDTO dto);
}