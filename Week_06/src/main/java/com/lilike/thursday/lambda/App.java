package com.lilike.thursday.lambda;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.lilike.thursday.domain.Person;

/**
 * @Author llk
 * @Date 2020/11/21 10:36
 * @Version 1.0
 */
public class App {

    public static void main(String[] args) {

        Person person = new Person(1,"Like");
        System.out.println(person.goodMorning(() -> "早安!"));;

    }


}
