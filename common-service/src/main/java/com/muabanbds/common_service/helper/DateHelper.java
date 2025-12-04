package com.muabanbds.common_service.helper;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static DateTimeFormatter formatterDateTime2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static String fromTimestampStd(Timestamp timestamp) {
        return timestamp.toLocalDateTime().format(formatterDateTime2);
    }
}
