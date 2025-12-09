package com.muabanbds.common_service.dto.systemDto.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThemeMenuRequest {
    String title;
    List<ThemeMenuRequest> children;

    @JsonSetter("children")
    public void setChildren(List<ThemeMenuRequest> children) {
        if (children != null) {
            this.children = children.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }
}
