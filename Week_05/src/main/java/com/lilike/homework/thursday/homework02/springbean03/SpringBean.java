package com.lilike.homework.thursday.homework02.springbean03;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author llk
 * @Date 2020/11/16 22:24
 * @Version 1.0
 */
@Configuration
@Import(ImportDog.class)
public class SpringBean {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringBean.class);
        ImportDog bean = context.getBean(ImportDog.class);
        bean.wang();

    }

}



class ImportDog {

    public void wang() {
        System.out.println("旺旺旺");
    }

}
