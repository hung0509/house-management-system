package com.muabanbds.proxy_client.business.identity.controller;

import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.dto.identityDto.response.PermissionResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.proxy_client.business.identity.service.AccountClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountController {
    AccountClientService clientService;

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(@PathVariable("username") String username) {
        return ResponseEntity.ok(this.clientService.findByUsername(username).getBody());
    }
}
