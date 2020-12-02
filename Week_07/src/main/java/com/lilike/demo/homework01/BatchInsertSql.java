package com.lilike.demo.homework01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 进行批量插入 一次插入1000条
 *
 * @Author llk
 * @Date 2020/12/2 14:48
 * @Version 1.0
 */
public class BatchInsertSql {

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

    public static void main(String[] args) throws SQLException {

        final int totalCount = 100000;
        conn.prepareStatement("truncate tt_order").execute();
        String sql = "INSERT INTO `tt_order` (`account_id`, `goods_id`, `pay_amount`, `pay_status`, `pay_time`, `refund_time`, `create_time`, `update_time`) VALUES ";
        long l = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            System.out.println("还需要执行: " + (10 - i) + "次");
            StringBuilder sb = new StringBuilder(sql);
            for (int j = 0; j < 1000; j++) {
                Order randomOrder = OrderDataGenerator.getRandomOrder();
                sb.append("( " + randomOrder.getAccountId() + ",");
                sb.append(" " + randomOrder.getGoodsId() + ",");
                sb.append(" " + randomOrder.getPayAmount() + ",");
                sb.append(" " + randomOrder.getPayStatus() + ",");
                sb.append("now() ,");
                sb.append("now() ,");
                sb.append("now() ,");
                sb.append("now() ),");
            }
            PreparedStatement preparedStatement = conn.prepareStatement(sb.toString().substring(0,sb.length()-1));
            preparedStatement.execute();
        }
        System.out.println("单条执行的运行时间是: " + (System.currentTimeMillis() - l) + "ms");
    }

}
