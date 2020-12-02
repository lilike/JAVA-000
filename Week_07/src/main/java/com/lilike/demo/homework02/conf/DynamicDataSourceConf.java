package com.lilike.demo.homework02.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.lilike.demo.homework02.ds.DatasourceKeyHolder;
import com.lilike.demo.homework02.ds.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author llk
 * @Date 2020/12/2 20:58
 * @Version 1.0
 */
@Configuration
public class DynamicDataSourceConf {


    @Bean
    public DataSource master() {
        DruidDataSource master = new DruidDataSource();
        master.setUrl("jdbc:mysql://localhost:3306/liyin?autoReconnect=true&serverTimezone=UTC");
        master.setUsername("root");
        master.setPassword("root");
        return master;
    }

    @Bean
    public DataSource slave1() {
        DruidDataSource master = new DruidDataSource();
        master.setUrl("jdbc:mysql://localhost:3316/liyin?autoReconnect=true&serverTimezone=UTC");
        master.setUsername("root");
        return master;
    }

    @Bean
    public DataSource slave2() {
        DruidDataSource master = new DruidDataSource();
        master.setUrl("jdbc:mysql://localhost:3316/liyin?autoReconnect=true&serverTimezone=UTC");
        master.setUsername("root");
        return master;
    }

    @Bean
    public DataSource dataSource(@Qualifier("master")DataSource master,@Qualifier("slave1") DataSource slave1,@Qualifier("slave2") DataSource slave2) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object,Object> map = new HashMap<>();
        map.put("master",master);
        map.put("slave1",slave1);
        map.put("slave2",slave2);
        dynamicDataSource.setTargetDataSources(map);
        dynamicDataSource.setDefaultTargetDataSource(master);
        dynamicDataSource.afterPropertiesSet();
        DatasourceKeyHolder.slaveKeys.add("slave1");
        DatasourceKeyHolder.slaveKeys.add("slave2");
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        return factory.getObject();
    }

}
