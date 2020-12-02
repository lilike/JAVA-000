package com.lilike.homework;

/**
 * @Author llk
 * @Date 2020/11/25 14:35
 * @Version 1.0
 */
public class DebugThread {

    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getId());
                System.out.println(Thread.currentThread().getPriority());
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();


    }



}
