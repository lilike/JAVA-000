package com.lilike.service;

/**
 * @Author llk
 * @Date 2020/11/18 19:29
 * @Version 1.0
 */
public class HelloworldService {

    private String words;

    public String sayHello() {
        return "hello: " + words;
    }


    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
