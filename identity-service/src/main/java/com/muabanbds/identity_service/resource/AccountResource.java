package com.muabanbds.identity_service.resource;

import com.muabanbds.common_service.dto.identityDto.request.AccountRequest;
import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.identity_service.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/accounts")
public class AccountResource {
    AccountService accountService;

    @GetMapping("/username/{username}")
    public ApiResponse<AccountResponse> findByUsername(@PathVariable String username) {
        log.info("***Log account resource - get account by username***");
        return accountService.findByUsername(username);
    }
}
