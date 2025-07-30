package com.muabanbds.proxy_client.business.identity.controller;

import com.muabanbds.common_service.dto.identityDto.request.AccountRequest;
import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.dto.identityDto.response.PermissionResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.proxy_client.business.identity.service.AccountClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountController {
    AccountClientService clientService;

    @GetMapping("/username/{username}")
    public ApiResponse<AccountResponse> getAccount(@PathVariable("username") String username) {
        log.info("*** Get, controller; get account by username  ***");
        return this.clientService.findByUsername(username);
    }

    @PostMapping
    public ApiResponse<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
        log.info("*** Save, controller; save account  ***");
        return clientService.create(accountRequest);
    }
}
