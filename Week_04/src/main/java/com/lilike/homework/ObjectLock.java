package com.lilike.homework;

/**
 *  对象锁
 * @Author llk
 * @Date 2020/11/9 19:46
 * @Version 1.0
 */
public class ObjectLock {

    public synchronized void hello() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello");
    }

    public  synchronized void world(){
        System.out.println("world");
    }

    public static void main(String[] args) {
        ObjectLock objectLock = new ObjectLock();
        new Thread(() -> objectLock.hello()).start();
        new Thread(() -> objectLock.world()).start();



    }




}
