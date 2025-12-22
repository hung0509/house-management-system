package com.muabanbds.proxy_client.business.system.controller;

import com.muabanbds.common_service.dto.systemDto.request.FirestoreDocumentRequest;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.proxy_client.business.system.service.FirestoreDocumentClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/themes")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FirestoreDocumentController {
    FirestoreDocumentClientService clientService;

    @PostMapping
    public ApiResponse<Void> update(@RequestBody FirestoreDocumentRequest req){
        log.info("***Log theme controller - update theme***");
        return clientService.update(req);
    }
}
