package com.lilike.demo.homework01.generator;

/**
 * @Author llk
 * @Date 2020/12/7 16:26
 * @Version 1.0
 */
public class GeneratorSQL {

    public static void main(String[] args) {

        String sql = "CREATE TABLE `tt_order%d` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',\n" +
                "  `account_id` bigint(20) NOT NULL COMMENT '用户ID',\n" +
                "  `goods_id` bigint(20) NOT NULL COMMENT '商品id',\n" +
                "  `pay_amount` int(255) DEFAULT NULL COMMENT '支付价格(单位分)',\n" +
                "  `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态0:未支付1:已经支付 2:已退款',\n" +
                "  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',\n" +
                "  `refund_time` timestamp NULL DEFAULT NULL COMMENT '退款时间',\n" +
                "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;\n";

        for (int i = 0; i < 16; i++) {
            String format = String.format(sql, i);
            System.out.println(format);
            System.out.println();
            System.out.println();
            System.out.println();

        }


    }

}
