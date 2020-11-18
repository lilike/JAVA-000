package com.lilike.homework.sunday.sigleton;

/**
 *  懒汉式
 *      需要的时候在加载,懒加载,线程不安全的版本
 *
 * @Author llk
 * @Date 2020/11/17 22:18
 * @Version 1.0
 */
public class Singleton01 {

    private Singleton01(){};

    private static Singleton01 instance;

    public static Singleton01 getInstance() {
        if (instance == null) {
            instance = new Singleton01();
        }
        return instance;
    }


}
