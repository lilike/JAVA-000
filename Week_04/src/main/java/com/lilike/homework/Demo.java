package com.lilike.homework;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author llk
 * @Date 2020/11/11 18:59
 * @Version 1.0
 */
public class Demo {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        Object obj = new Object();

        synchronized (obj) {
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("新线程");
            }).start();
        }


        synchronized (obj) {
            System.out.println("主线程");
        }




    }

}

