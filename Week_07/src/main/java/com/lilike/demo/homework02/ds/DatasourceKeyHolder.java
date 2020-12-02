package com.lilike.demo.homework02.ds;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author llk
 * @Date 2020/12/2 20:57
 * @Version 1.0
 */
public class DatasourceKeyHolder {

    public static final List<String> slaveKeys = new ArrayList<>();

    public static final ThreadLocal<String> datasourceHolder = new ThreadLocal<>();

    public static String get() {
        return datasourceHolder.get();
    }

    public static void set(String key) {
        datasourceHolder.set(key);
    }

    public static void clear() {
        datasourceHolder.remove();
    }
}
