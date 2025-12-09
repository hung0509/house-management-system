package com.muabanbds.system_service.resource;

import com.muabanbds.common_service.dto.systemDto.request.ThemeMenuRequest;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.system_service.service.ThemeMenuService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/themes/menu")
public class ThemeMenuResource {
    private final ThemeMenuService themeMenuService;

    @GetMapping
    public ApiResponse<List<ThemeMenuRequest>> getMenu(){
        log.info("***Log theme menu resource - get theme menu***");
        return themeMenuService.getMenu();
    }

    @PutMapping
    public ApiResponse<Void> update(@RequestBody List<ThemeMenuRequest> themeMenuRequests){
        log.info("***Log theme menu resource - update theme menu***");
        return themeMenuService.update(themeMenuRequests);
    }
}
