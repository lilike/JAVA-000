package com.lilike.thursday.stream;

import com.lilike.thursday.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author llk
 * @Date 2020/11/21 10:27
 * @Version 1.0
 */
public class ToMapDemo {

    public static void main(String[] args) {

        Person p1 = new Person(1,"like");
        Person p2 = new Person(2,"yinyin");
        Person p3 = new Person(2,"maoyin");

        List<Person> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        Map<Integer, Person> collect = list.stream().collect(Collectors.toMap(x -> x.getId(), y -> y,(a,b) -> a));
        System.out.println(collect);
}

}

