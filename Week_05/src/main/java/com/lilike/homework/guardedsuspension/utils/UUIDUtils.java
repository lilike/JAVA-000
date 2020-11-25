package com.lilike.homework.guardedsuspension.utils;

import java.util.UUID;

/**
 * @Author llk
 * @Date 2020/11/19 15:10
 * @Version 1.0
 */
public final class UUIDUtils {

    public static String getUUID() {
        UUID uuid =UUID.randomUUID();
        String str = uuid.toString();
        return str;
    }
}
