package com.lilike.thursday.guava.collection;

import com.google.common.collect.*;
import com.lilike.thursday.domain.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author llk
 * @Date 2020/11/21 10:46
 * @Version 1.0
 */
public class CollectionDemo {

    public static void main(String[] args) {

        // 直接调用静态方法创建集合对象
        List<String> str = Lists.newArrayList();

        // 使用不可变对象,可以保证线程安全
        ImmutableList<Integer> of = ImmutableList.of(1, 2, 3);

        // 代替 Map<String,List<String>> Multimap: key-value  key可以重复
        ArrayListMultimap<Integer, Person> map = ArrayListMultimap.create();
        map.put(1,new Person(1,"like"));
        map.put(1,new Person(2,"likeyou"));
        System.out.println(map.get(1));

        // multiSet 可以有统计的作用
        Multiset<String> multiset = HashMultiset.create();
        String words = "I like you,and do you like me";
        String[] s = words.split(" ");
        for (String s1 : s) {
            multiset.add(s1);
        }

        int count = multiset.count("like");
        System.out.println(count);


        //BiMap: 双向Map(Bidirectional Map) 键与值都不能重复
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("like","xuyin");
        biMap.put("maoge","jiangge");


        //


    }

}
