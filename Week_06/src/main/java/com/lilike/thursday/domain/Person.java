package com.lilike.thursday.domain;

import com.lilike.thursday.lambda.inter.MyFunction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author llk
 * @Date 2020/11/21 10:28
 * @Version 1.0
 */
@Data
@ToString
@EqualsAndHashCode
public class Person {

    private Integer id;

    private String name;

    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String goodMorning(MyFunction<String> myFunction) {
        return myFunction.get();
    }

}
