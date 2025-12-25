package com.hse.gozon.gateway.client;

import com.hse.gozon.dto.account.AccountCreateDto;
import com.hse.gozon.dto.account.AccountDto;
import com.hse.gozon.dto.account.DepDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payments-service", url = "${payments-service.url:http://localhost:9090}")
public interface AccountClient {

    @PostMapping("/api/accounts")
    AccountDto createAccount(@RequestBody AccountCreateDto newAccount);

    @GetMapping("/api/accounts/{accountId}")
    AccountDto findById(@PathVariable Integer accountId);

    @PostMapping("/api/accounts/deposit")
    AccountDto deposit(@RequestBody DepDto depDto);
}
