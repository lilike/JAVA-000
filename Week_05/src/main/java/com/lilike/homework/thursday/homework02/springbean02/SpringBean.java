package com.lilike.homework.thursday.homework02.springbean02;

import com.lilike.homework.domain.Dog;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author llk
 * @Date 2020/11/16 22:18
 * @Version 1.0
 */
public class SpringBean {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringBeanConfiguration.class);
        Dog bean = context.getBean(Dog.class);
        System.out.println(bean.getName());

    }


}

@Configuration
class SpringBeanConfiguration {

    @Bean
    public Dog hotDog(){
        Dog dog = new Dog();
        dog.setId(2);
        dog.setName("热狗");
        return dog;
    }

}

