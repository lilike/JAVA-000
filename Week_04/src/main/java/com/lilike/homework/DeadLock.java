package com.lilike.homework;

/**
 * @Author llk
 * @Date 2020/11/9 19:40
 * @Version 1.0
 */
public class DeadLock {

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void operator1 () {
        synchronized (lock1) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + "operator1");
            }
        }
    }


    public void operator2 () {
        synchronized (lock2) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + "operator2");
            }
        }
    }




    public static void main(String[] args) {

        final DeadLock deadLock = new DeadLock();
        new Thread(() -> deadLock.operator1()).start();

        new Thread(() -> deadLock.operator2()).start();



    }

}
