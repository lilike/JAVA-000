package com.lilike.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Author llk
 * @Date 2020/11/19 11:33
 * @Version 1.0
 */
public class ForkJoinPoolDemo {

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        Integer invoke = forkJoinPool.invoke(new Fib(10));
        System.out.println(invoke);


    }

}


class Fib extends RecursiveTask<Integer> {

    private Integer num;

    public Fib(Integer num) {
        this.num = num;
    }

    @Override
    protected Integer compute() {
        System.out.println(Thread.currentThread().getName() + "this thread num :"  + num);
        if (num <= 1) return num;

        Fib f1 = new Fib(num-1);
        f1.fork();

        Fib f2 = new Fib(num-2);

        return f2.compute() + f1.join();
    }
}
