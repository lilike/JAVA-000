package com.lilike.properteis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *  如果没有就使用默认值,约定大于配置
 * @Author llk
 * @Date 2020/11/18 19:30
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "spring.lilike")
public class HelloworldProperties {

    public static final String DEFAULT_WORDS = "world";

    public String words = DEFAULT_WORDS;

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
