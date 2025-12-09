package com.muabanbds.proxy_client.business.system.controller;

import com.muabanbds.common_service.dto.systemDto.request.ThemeMenuRequest;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.proxy_client.business.system.service.ThemeMenuClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/themes/menu")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ThemeMenuController {
    ThemeMenuClientService clientService;

    @GetMapping
    public ApiResponse<List<ThemeMenuRequest>> getMenu(){
        log.info("***Log theme menu controller - get theme menu***");
        return this.clientService.getMenu();
    }

    @PutMapping
    public ApiResponse<Void> update(@RequestBody List<ThemeMenuRequest> themeMenuRequests){
        log.info("***Log theme menu resource - update theme menu***");
        return this.clientService.update(themeMenuRequests);
    }
}
