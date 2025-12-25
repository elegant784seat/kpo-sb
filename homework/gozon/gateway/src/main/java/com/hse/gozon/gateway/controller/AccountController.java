package com.hse.gozon.gateway.controller;

import com.hse.gozon.dto.account.AccountCreateDto;
import com.hse.gozon.dto.account.AccountDto;
import com.hse.gozon.dto.account.DepDto;
import com.hse.gozon.gateway.client.AccountClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Tag(name = "Управление аккаунтами",
        description = "API для управления пользовательскими аккаунтами")
public class AccountController {
    private final AccountClient accountClient;

    @PostMapping
    @Operation(summary = "Создать новый аккаунт",
            description = "Создает новый аккаунт пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Аккаунт успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
            @ApiResponse(responseCode = "409", description = "Аккаунт с таким email уже существует")
    })
    public AccountDto createAccount(@Validated @RequestBody AccountCreateDto newAccount) {
        return accountClient.createAccount(newAccount);
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Получить информацию об аккаунте",
            description = "Возвращает информацию об аккаунте по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация об аккаунте"),
            @ApiResponse(responseCode = "404", description = "Аккаунт не найден")
    })
    public AccountDto findById(@Parameter(description = "ID аккаунта", example = "1", required = true)
                               @PathVariable Integer accountId) {
        return accountClient.findById(accountId);
    }

    @PatchMapping("/deposit")
    @Operation(summary = "Пополнить счет",
            description = "Пополняет баланс указанного аккаунта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Счет успешно пополнен"),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
            @ApiResponse(responseCode = "404", description = "Аккаунт не найден")
    })
    public AccountDto deposit(@Validated @RequestBody DepDto depDto) {
        return accountClient.deposit(depDto);
    }
}
