package com.lilike.homework;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 进行批量处理任务,然后把数据保存到阻塞队列里面
 *
 *
 * @Author llk
 * @Date 2020/11/19 10:52
 * @Version 1.0
 */
public class CompletionServiceDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Random random = new Random();
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor,new ArrayBlockingQueue<>(10));

        for (int i = 0; i < 10; i++) {
            completionService.submit(new MyRunnable(Double.valueOf(random.nextDouble() * 10000.0).intValue()));
        }




    }


}


class MyRunnable implements Callable<Integer> {

    private Integer millSecond;

    public MyRunnable(Integer millSecond) {
        this.millSecond = millSecond;
    }

    @Override
    public Integer call() {

        System.out.println( Thread.currentThread().getName() + "我要睡" + millSecond + "秒");

        try {
            Thread.sleep(millSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return millSecond;
    }
}