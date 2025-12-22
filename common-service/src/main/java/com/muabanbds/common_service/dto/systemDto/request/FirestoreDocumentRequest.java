package com.muabanbds.common_service.dto.systemDto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FirestoreDocumentRequest {
    String collection;
    String document;
    String field;
    Object json;
}
