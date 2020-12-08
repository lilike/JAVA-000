package com.lilike.demo.homework01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Author llk
 * @Date 2020/12/7 15:26
 * @Version 1.0
 */
@EnableAspectJAutoProxy
@EnableScheduling
@MapperScan("com.lilike.demo.homework01.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Homework01Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Homework01Application.class, args);
        Map<String, DataSource> beansOfType = run.getBeansOfType(DataSource.class);
        System.out.println(beansOfType);


    }

}
