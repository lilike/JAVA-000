package com.lilike.homework;

import java.util.concurrent.*;

/**
 * @Author llk
 * @Date 2020/11/19 10:30
 * @Version 1.0
 */
public class CompletableFutureDemo {

    /**
     *  CompletableFuture 是一个可以很容易将多个线程进行组合起来的一个工具类
     *      让程序员可以基于任务层面去做编程,而不需要关心线程之间的
     *           串行 组合 通讯等问题
     *
     *  实现线程之间进行相互组合
     *
     * @param args
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CompletableFuture<Void> f1 = CompletableFuture.runAsync( () -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("洗水壶");
            sleep();
        },executorService).thenRunAsync(() -> {
            System.out.println("烧开水");
            sleep();
        },executorService);


        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());

            System.out.println("洗茶壶");
            sleep();
            System.out.println("洗茶杯");
            sleep();
            System.out.println("拿茶叶");
            return "龙井茶叶";
        },executorService);


        CompletableFuture<String> f3 = f1.thenCombine(f2, (__,cy) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("t1拿到了茶叶");
            System.out.println("泡茶");
            return "上茶" + cy;
        });


        System.out.println(f3.get());



    }

    static void sleep() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
