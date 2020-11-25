package com.lilike.thursday.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author llk
 * @Date 2020/11/21 11:14
 * @Version 1.0
 */
public class CacheDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

//        loadingCache();

        Cache<String, String> cache = CacheBuilder.newBuilder().build();

        String s = cache.get("like", () -> {
            System.out.println("捞取数据");
            return "hello";
        });

        System.out.println(s);

        Thread.sleep(1000);

        System.out.println(cache.getIfPresent("like"));

    }

    private static void loadingCache() throws ExecutionException, InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(2)
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                            @Override
                            public String load(String key) throws Exception {
                                System.out.println("去数据库拿取数据");
                                return "hello " + key;
                            }
                        });

        cache.put("lilike","yinyin");
        System.out.println(cache.get("lilike"));

        Thread.sleep(3000);

        System.out.println(cache.get("lilike"));
    }

}
