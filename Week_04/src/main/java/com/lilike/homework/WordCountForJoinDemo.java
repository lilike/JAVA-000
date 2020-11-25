package com.lilike.homework;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 *  用来计算单词的数量
 *
 * @Author llk
 * @Date 2020/11/19 11:42
 * @Version 1.0
 */
public class WordCountForJoinDemo {


    public static void main(String[] args) {

        String[] fc = {"hello world", "hello me", "hello fork", "hello join", "fork join in world"};

        ForkJoinPool pool = new ForkJoinPool();
        WordCount task = new WordCount(fc);
        pool.invoke(task);
        System.out.println(task.join());
    }
}


class WordCount extends RecursiveTask<Map<String,Integer>> {

    private String[] arr;

    public WordCount(String[] arr) {
        this.arr = arr;
    }

    @Override
    protected Map<String,Integer> compute() {

        if (arr.length == 1) {
            Map<String,Integer> map = new HashMap<>();
            String words = arr[0];
            String[] s = words.split(" ");
            for (String s1 : s) {
                int count = map.getOrDefault(s1, 0) + 1;
                map.put(s1,count);
            }
            return map;
        }

        // 进行切分
        int mid = (arr.length-1)/2;
        String[] arr1 = Arrays.copyOfRange(arr, 0, mid+1);
        String[] arr2 = Arrays.copyOfRange(arr, mid+1, arr.length);
        WordCount wordCount1 = new WordCount(arr1);
        WordCount wordCount2 = new WordCount(arr2);
        wordCount2.fork();

        Map<String, Integer> compute = wordCount1.compute();

        Map<String, Integer> join = wordCount2.join();
        for (String s : join.keySet()) {
            int count = join.get(s);
            compute.put(s,count+ compute.getOrDefault(s,0));
        }

        return compute;
    }
}
