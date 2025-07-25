package com.muabanbds.identity_service.service;

import com.muabanbds.common_service.dto.identityDto.request.AccountRequest;
import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.helper.BaseServiceGeneric;
import com.muabanbds.common_service.payload.ApiResponse;

public interface AccountService extends BaseServiceGeneric<Integer, AccountRequest, AccountResponse> {
    ApiResponse<AccountResponse> findByUsername(String username);
}
