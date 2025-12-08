package com.muabanbds.common_service.dto.systemDto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThemeMenuRequest {
    String title;
    List<ThemeMenuRequest> children;
}
