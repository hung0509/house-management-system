package com.muabanbds.identity_service.service;

import com.muabanbds.common_service.dto.identityDto.request.InvalidatedTokenRequest;
import com.muabanbds.common_service.dto.identityDto.response.InvalidatedTokenResponse;
import com.muabanbds.common_service.payload.ApiResponse;

public interface InvalidatedTokenService {
     ApiResponse<InvalidatedTokenResponse> invalidate(InvalidatedTokenRequest req);
}
