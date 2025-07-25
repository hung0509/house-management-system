package com.muabanbds.common_service.sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {

    private List<ParameterInfo> list = new ArrayList<>();


    public void add(String col, Object val, String logical, String relational, String status) {
        list.add(new ParameterInfo(col, val, logical, relational, status));
    }

    public String listToJson() {
        // thanhnc: dung hasmap && objectmapper de cast ve string khong bi dao lon thu tu
        try {
            Integer stt = 1;
            Map<String, Object> orderedMap = new LinkedHashMap<>();

            for (ParameterInfo param : this.list) {
                Object jsonValue = ParameterInfo.toJson(param);

                if (jsonValue instanceof JSONObject) {
                    orderedMap.put(stt.toString(), new ObjectMapper().readValue(jsonValue.toString(), Map.class));
                } else {
                    orderedMap.put(stt.toString(), jsonValue);
                }
                stt++;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
            return objectMapper.writeValueAsString(orderedMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String listToJson1() throws Exception {
        Integer stt = 1;
        Map<String, Object> orderedMap = new LinkedHashMap<>();

        for (ParameterInfo param : this.list) {
            orderedMap.put(stt.toString(), ParameterInfo.toJson(param));
            stt++;
        }
        System.out.println("orderedMap: " + orderedMap);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
        return objectMapper.writeValueAsString(orderedMap);
    }
}
