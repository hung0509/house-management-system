package com.muabanbds.system_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.muabanbds.common_service.dto.systemDto.request.FirestoreDocumentRequest;
import com.muabanbds.system_service.service.FirestoreDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirestoreDocumentServiceImpl implements FirestoreDocumentService {

    private final Firestore firestore;
    private final ObjectMapper objectMapper;

    /**
     * Đọc field dạng List<T> từ một document Firestore
     */
    @Override
    public <T> List<T> readList(String collection, String document, String field, Class<T> clazz) {
        try {
            DocumentReference ref = firestore.collection(collection).document(document);
            DocumentSnapshot snapshot = ref.get().get();

            if (!snapshot.exists()) return Collections.emptyList();

            List<?> rawList = snapshot.get(field, List.class);
            if (rawList == null) return Collections.emptyList();

            return objectMapper.convertValue(
                    rawList,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
            );

        } catch (Exception e) {
            log.error("Error reading Firestore list {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Ghi đè một List<T> vào field của document
     */
    @Override
    public <T> void writeList(String collection, String document, String field, List<T> data) {
        try {
            firestore.collection(collection)
                    .document(document)
                    .update(field, data)
                    .get();
        } catch (Exception e) {
            log.error("Error writing Firestore list {}", e.getMessage());
        }
    }

    @Override
    public void updateListByJson(FirestoreDocumentRequest req) {
        firestore.collection(req.getCollection())
                .document(req.getDocument())
                .update(req.getField(), req.getJson());
    }

}

