package com.lilike.demo.homework01.demo;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author llk
 * @Date 2020/12/7 15:27
 * @Version 1.0
 */
public class ShardingJDBCDemo {

    public static void main(String[] args) throws SQLException {

// 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

// 配置第 1 个数据源
        BasicDataSource dataSource1 = new BasicDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/like0?serverTimezone=UTC");
        dataSource1.setUsername("root");
        dataSource1.setPassword("root");
        dataSourceMap.put("like0", dataSource1);
        System.out.println("第一个库");
// 配置第 2 个数据源
        BasicDataSource dataSource2 = new BasicDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://localhost:3316/like1?serverTimezone=UTC");
        dataSource2.setUsername("root");
        dataSource2.setPassword("");
        dataSourceMap.put("like1", dataSource2);
        System.out.println("第二个库");

// 配置 t_order 表规则
        ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("tt_order", "like${0..1}.tt_order${0..1}");

// 配置分库策略
        orderTableRuleConfig.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("account_id", "dbShardingAlgorithm"));

// 配置分表策略
        orderTableRuleConfig.setTableShardingStrategy(new StandardShardingStrategyConfiguration("goods_id", "tableShardingAlgorithm"));

// 省略配置 t_order_item 表规则...
// ...

// 配置分片规则
// 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTables().add(orderTableRuleConfig);

// 配置分库算法
        Properties dbShardingAlgorithmrProps = new Properties();
        dbShardingAlgorithmrProps.setProperty("algorithm-expression", "like${account_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", dbShardingAlgorithmrProps));

// 配置分表算法
        Properties tableShardingAlgorithmrProps = new Properties();
        tableShardingAlgorithmrProps.setProperty("algorithm-expression", "tt_order${goods_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", tableShardingAlgorithmrProps));

// 创建 ShardingSphereDataSource
        DataSource dataSource = ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, Collections.singleton(shardingRuleConfig), new Properties());

        String sql = "INSERT INTO `tt_order` (`account_id`, `goods_id`, `pay_amount`, `pay_status`, `pay_time`, `refund_time`, `create_time`, `update_time`) VALUES";
        String value = " (?, ?, ?, ?, now(), now(), now(), now())";

        try (
                Connection conn = dataSource.getConnection();
        ){
            PreparedStatement preparedStatement = conn.prepareStatement(sql + value);
            for (int i = 0; i < 100; i++) {
                System.out.println("还剩下:" + (100 - i + 1) + "条");
                Order randomOrder = OrderDataGenerator.getRandomOrder();
                preparedStatement.setObject(1,randomOrder.getAccountId());
                preparedStatement.setObject(2,randomOrder.getGoodsId());
                preparedStatement.setObject(3,randomOrder.getPayAmount());
                preparedStatement.setObject(4,randomOrder.getPayStatus());
                preparedStatement.execute();
            }

        }


    }

}
