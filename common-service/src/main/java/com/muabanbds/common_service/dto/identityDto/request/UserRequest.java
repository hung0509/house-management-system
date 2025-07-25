package com.muabanbds.common_service.dto.identityDto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    Integer id;
    String fullName;
    Instant dateOfBirth;
    String gender;
    String email;
    String phone;
    String address;
    String description;
}
