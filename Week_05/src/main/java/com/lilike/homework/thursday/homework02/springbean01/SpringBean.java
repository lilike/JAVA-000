package com.lilike.homework.thursday.homework02.springbean01;

import com.lilike.homework.domain.Dog;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author llk
 * @Date 2020/11/16 20:36
 * @Version 1.0
 */
public class SpringBean {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/springbean01.xml");

        Dog bean = context.getBean(Dog.class);
        System.out.println(bean.getName());

    }

}
