package com.lilike.homework;

import com.lilike.domain.Lilike;
import com.lilike.service.HelloworldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author llk
 * @Date 2020/11/16 20:08
 * @Version 1.0
 */
@SpringBootApplication
public class MetaDataApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MetaDataApplication.class, args);

        Lilike bean = run.getBean(Lilike.class);
        System.out.println(bean.getName());

        HelloworldService service = run.getBean(HelloworldService.class);
        System.out.println(service.sayHello());;

    }

}