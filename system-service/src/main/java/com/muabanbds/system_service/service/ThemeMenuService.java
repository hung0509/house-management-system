package com.muabanbds.system_service.service;

import com.muabanbds.common_service.dto.systemDto.request.ThemeMenuRequest;
import com.muabanbds.common_service.payload.ApiResponse;

import java.util.List;

public interface ThemeMenuService {
    ApiResponse<List<ThemeMenuRequest>> getMenu();
    ApiResponse<Void> update(List<ThemeMenuRequest> themeMenuRequest);
}
