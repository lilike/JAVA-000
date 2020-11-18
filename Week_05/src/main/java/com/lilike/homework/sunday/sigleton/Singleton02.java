package com.lilike.homework.sunday.sigleton;

/**
 *  懒汉 懒加载 线程安全 双重检测
 *  首先sync关键字是加在静态方法上面,
 *  锁对象是Class对象
 *  那么在每一次调用这个方法都是串行的,虽然线程安全,但是性能很差
 *
 *
 * @Author llk
 * @Date 2020/11/17 22:20
 * @Version 1.0
 */
public class Singleton02 {

    private Singleton02() {
    }


    private static Singleton02 instance;

    public static synchronized Singleton02 getInstance() {

        if (instance == null) {
            instance = new Singleton02();
        }
        return instance;
    }


}
