package com.bootme.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class TimeConverter {

    private TimeConverter(){
    }

    public static long convertLocalDateTimeToLong(LocalDateTime localDateTime){
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Seoul"));
        return zonedDateTime.toInstant().toEpochMilli();
    }

}
