package com.lilike.demo.homework02;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.lilike.demo.homework02.conf.DynamicDataSourceConf;
import com.lilike.demo.homework02.ds.DynamicDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author llk
 * @Date 2020/12/2 20:31
 * @Version 1.0
 */
@EnableAspectJAutoProxy
@EnableScheduling
@MapperScan("com.lilike.demo.homework02.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class Week07Application {
    public static void main(String[] args) {
        SpringApplication.run(Week07Application.class, args);
    }

}
