电商平台SQL 

```SQL

-- 账户
CREATE TABLE `tt_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `nick_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `status` tinyint(4) DEFAULT '0' COMMENT '账户状态0:有效1:注销2:other',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 商品名
CREATE TABLE `tt_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `goods_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `goods_image_url` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '商品图片地址',
  `goods_price` int(10) NOT NULL COMMENT '商品价格单位（分）',
  `goods_content` varchar(255) COLLATE utf8_bin NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:未上架1:已经上架',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 商品订单
CREATE TABLE `tt_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `account_id` bigint(20) NOT NULL COMMENT '用户ID',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `pay_amount` int(255) DEFAULT NULL COMMENT '支付价格(单位分)',
  `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态0:未支付1:已经支付 2:已退款',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_acc_status` (`account_id`,`pay_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;




```