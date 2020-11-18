package com.lilike.homework.sunday.sigleton;

/**
 *  饿汉式,直接new出来,可以保证线程安全,但是性能比较差,浪费启动资源
 *
 * @Author llk
 * @Date 2020/11/17 22:27
 * @Version 1.0
 */
public class Singleton04 {

    private Singleton04() {}

    private static Singleton04 instance = new Singleton04();

    public static Singleton04 getInstance() {
        return instance;
    }

}
