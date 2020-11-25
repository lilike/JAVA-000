package com.lilike.homework;

import com.lilike.domain.Lilike;
import com.lilike.service.HelloworldService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author llk
 * @Date 2020/11/16 20:08
 * @Version 1.0
 */
@SpringBootApplication
@RestController
public class MetaDataApplication {

    private static final ThreadLocal<Date> local = new ThreadLocal();


    @RequestMapping("/hello")
    public void hello() {
        Date date = new Date();
        if (local.get() == null) {
            local.set(date);
        }
        System.out.println(Thread.currentThread().getName() +"===============" + "当前时间: " + date + "本地变量的时间" + local.get());
    }


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MetaDataApplication.class, args);

        Lilike bean = run.getBean(Lilike.class);
        System.out.println(bean.getName());

        HelloworldService service = run.getBean(HelloworldService.class);
        System.out.println(service.sayHello());

    }

}