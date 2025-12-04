package com.muabanbds.identity_service.service.impl;

import com.muabanbds.common_service.dto.identityDto.request.InvalidatedTokenRequest;
import com.muabanbds.common_service.dto.identityDto.response.InvalidatedTokenResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.identity_service.repository.InvalidatedTokenRepository;
import com.muabanbds.identity_service.service.InvalidatedTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InvalidatedTokenServiceImpl implements InvalidatedTokenService {
    InvalidatedTokenRepository invalidatedTokenRepository;

    @Override
    public ApiResponse<InvalidatedTokenResponse> invalidate(InvalidatedTokenRequest req) {
        log.info("***Log account service - get account by username***");
        log.info("{req} :" + req);

        Boolean isCheck = invalidatedTokenRepository.existsByTokenId(req.getToken());

        return ApiResponse.<InvalidatedTokenResponse>builder()
                .result(InvalidatedTokenResponse.builder()
                        .isValid(isCheck)
                        .build())
                .build();
    }
}
