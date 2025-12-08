package com.muabanbds.system_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.muabanbds.common_service.dto.systemDto.request.ThemeMenuRequest;
import com.muabanbds.system_service.service.ThemeMenuService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ThemeMeneServiceImpl implements ThemeMenuService {
    Firestore firestore;
    ObjectMapper objectMapper;

    private DocumentReference getMenuDocument(String document) {
        return firestore.collection("theme").document(document);
    }

    // Lấy menu
    public List<ThemeMenuRequest> getMenu(String document) throws Exception {
        DocumentSnapshot doc = getMenuDocument(document).get().get();

        if (!doc.exists()) return new ArrayList<>();

        List<Map<String, Object>> rawList = (List<Map<String, Object>>) doc.get("menuItems");

        List<ThemeMenuRequest> result = new ArrayList<>();

        if(rawList==null || rawList.isEmpty())
            return new ArrayList<>();

        for (Map<String, Object> map : rawList) {
            ThemeMenuRequest dto = objectMapper.convertValue(map, ThemeMenuRequest.class);
            result.add(dto);
        }

        return result;
    }

    // Ghi đè toàn bộ menu
    public void updateMenu(List<ThemeMenuRequest> newMenu, String document) throws Exception {
        getMenuDocument(document).update("menuItems", newMenu).get();
    }

    // Thêm 1 item
    public void addMenuItem(ThemeMenuRequest item, String document) throws Exception {
        List<ThemeMenuRequest> current = getMenu(document);
        current.add(item);
        updateMenu(current, document);
    }

    // Cập nhật 1 menu item
    public void updateMenuItem(int index, ThemeMenuRequest updated, String document) throws Exception {
        List<ThemeMenuRequest> current = getMenu(document);
        current.set(index, updated);
        updateMenu(current, document);
    }

    // Xoá 1 item
    public void deleteMenuItem(int index, String document) throws Exception {
        List<ThemeMenuRequest> current = getMenu(document);
        current.remove(index);
        updateMenu(current, document);
    }
}
