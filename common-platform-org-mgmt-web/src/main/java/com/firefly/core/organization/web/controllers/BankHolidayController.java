/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.organization.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.organization.core.services.BankHolidayService;
import com.firefly.core.organization.interfaces.dtos.BankHolidayDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/holidays")
@Tag(name = "Bank Holiday Management", description = "APIs for managing bank holidays")
public class BankHolidayController {

    @Autowired
    private BankHolidayService bankHolidayService;

    @Operation(summary = "Get all bank holidays with filtering", description = "Returns a paginated list of bank holidays based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bank holidays",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PaginationResponse<BankHolidayDTO>> filterBankHolidays(
            @Parameter(description = "Filter criteria for bank holidays", required = true)
            @Valid @RequestBody FilterRequest<BankHolidayDTO> filterRequest) {
        return bankHolidayService.filterBankHolidays(filterRequest);
    }

    @Operation(summary = "Create a new bank holiday", description = "Creates a new bank holiday with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bank holiday successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankHolidayDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bank holiday data supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankHolidayDTO> createBankHoliday(
            @Parameter(description = "Bank holiday details to create", required = true)
            @Valid @RequestBody BankHolidayDTO bankHolidayDTO) {
        return bankHolidayService.createBankHoliday(bankHolidayDTO);
    }

    @Operation(summary = "Get bank holiday by ID", description = "Returns a bank holiday based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bank holiday",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankHolidayDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bank holiday not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{holidayId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankHolidayDTO> getBankHolidayById(
            @Parameter(description = "ID of the bank holiday to retrieve", required = true)
            @PathVariable UUID holidayId) {
        return bankHolidayService.getBankHolidayById(holidayId);
    }

    @Operation(summary = "Update bank holiday", description = "Updates an existing bank holiday with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bank holiday successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankHolidayDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bank holiday data supplied"),
            @ApiResponse(responseCode = "404", description = "Bank holiday not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{holidayId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankHolidayDTO> updateBankHoliday(
            @Parameter(description = "ID of the bank holiday to update", required = true)
            @PathVariable UUID holidayId,
            @Parameter(description = "Updated bank holiday details", required = true)
            @Valid @RequestBody BankHolidayDTO bankHolidayDTO) {
        return bankHolidayService.updateBankHoliday(holidayId, bankHolidayDTO);
    }

    @Operation(summary = "Delete bank holiday", description = "Deletes a bank holiday based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bank holiday successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Bank holiday not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{holidayId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBankHoliday(
            @Parameter(description = "ID of the bank holiday to delete", required = true)
            @PathVariable UUID holidayId) {
        return bankHolidayService.deleteBankHoliday(holidayId);
    }
}