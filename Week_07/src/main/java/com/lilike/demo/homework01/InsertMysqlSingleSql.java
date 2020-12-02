package com.lilike.demo.homework01;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author llk
 * @Date 2020/12/2 14:23
 * @Version 1.0
 */
public class InsertMysqlSingleSql {

    static Connection conn = null;

    static {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/shop?serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "root");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 插入 100 万订单模拟数据，测试不同方式的插入效率
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        final int totalCount = 10000;
        conn.prepareStatement("truncate tt_order").execute();
        String sql = "INSERT INTO `tt_order` (`account_id`, `goods_id`, `pay_amount`, `pay_status`, `pay_time`, `refund_time`, `create_time`, `update_time`) VALUES";
        long l = System.currentTimeMillis();
        singleInsert(totalCount, sql);
        System.out.println("单条执行的运行时间是: " + (System.currentTimeMillis() - l) + "ms");
    }


    private static void singleInsert(int totalCount, String sql) throws SQLException {
        String value = " (?, ?, ?, ?, now(), now(), now(), now())";
        PreparedStatement preparedStatement = conn.prepareStatement(sql + value);
        for (int i = 0; i < totalCount; i++) {
            System.out.println("还剩下:" + (totalCount - i + 1) + "条");
            Order randomOrder = OrderDataGenerator.getRandomOrder();
            preparedStatement.setObject(1,randomOrder.getAccountId());
            preparedStatement.setObject(2,randomOrder.getGoodsId());
            preparedStatement.setObject(3,randomOrder.getPayAmount());
            preparedStatement.setObject(4,randomOrder.getPayStatus());
            preparedStatement.execute();
        }
    }

}
