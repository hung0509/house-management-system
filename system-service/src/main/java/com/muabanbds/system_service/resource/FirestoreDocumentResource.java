package com.muabanbds.system_service.resource;

import com.muabanbds.common_service.dto.systemDto.request.FirestoreDocumentRequest;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.system_service.service.FirestoreDocumentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/themes")
public class FirestoreDocumentResource {
    FirestoreDocumentService firestoreDocumentService;

    @PostMapping
    public ApiResponse<Void> update(@RequestBody FirestoreDocumentRequest req){
        log.info("***Log theme menu resource - update theme menu***");
        firestoreDocumentService.updateListByJson(req);

        return ApiResponse.<Void>builder()
                .build();
    }
}
