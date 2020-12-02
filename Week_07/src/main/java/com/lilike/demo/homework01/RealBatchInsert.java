package com.lilike.demo.homework01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author llk
 * @Date 2020/12/2 15:18
 * @Version 1.0
 */
public class RealBatchInsert {

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
     * 插入10W
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        long l = System.currentTimeMillis();

        conn.setAutoCommit(false);
        conn.prepareStatement("truncate tt_order").execute();
        String sql = "INSERT INTO `tt_order` (`account_id`, `goods_id`, `pay_amount`, `pay_status`, `pay_time`, `refund_time`, `create_time`, `update_time`) VALUES ";
        String value = " (?, ?, ?, ?, now(), now(), now(), now())";
        PreparedStatement preparedStatement = conn.prepareStatement(sql + value);

        for (int j = 0; j < 1000; j++) {
                System.out.println("还需要执行 :" + (1000-j) + "次" );
            for (int i = 0; i < 1000; i++) {
                Order randomOrder = OrderDataGenerator.getRandomOrder();
                preparedStatement.setObject(1,randomOrder.getAccountId());
                preparedStatement.setObject(2,randomOrder.getGoodsId());
                preparedStatement.setObject(3, randomOrder.getPayAmount());
                preparedStatement.setObject(4,randomOrder.getPayStatus());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            conn.commit();
            preparedStatement.clearBatch();
        }
        preparedStatement.close();
        conn.close();
        System.out.println("执行的运行时间是: " + (System.currentTimeMillis() - l) + "ms");

    }
}
