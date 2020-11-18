package com.lilike.homework.sunday.sigleton;

/**
 *  懒加载的方式
 *      voliate 关键字 + 双重检测
 *      如果不为null 就不会进入锁对象里面,所以就不会串行
 *      同时锁的话一定要锁class对象,不然就没效果
 *
 *
 * @Author llk
 * @Date 2020/11/17 22:23
 * @Version 1.0
 */
public class Singleton03 {

    private Singleton03(){}

    private volatile static Singleton03 instance;

    public static Singleton03 getInstance() {
        if (instance == null) {
            // 只锁这里保证性能
            synchronized (Singleton03.class) {
                if (instance == null) {
                    instance = new Singleton03();
                }
            }
        }
        return instance;
    }

}
