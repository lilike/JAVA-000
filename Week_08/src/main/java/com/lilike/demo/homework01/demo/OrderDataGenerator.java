package com.lilike.demo.homework01.demo;

import java.util.Date;
import java.util.Random;

/**
 * @Author llk
 * @Date 2020/12/2 14:26
 * @Version 1.0
 */
public class OrderDataGenerator {

    public static final Random random = new Random();

    public static Order getRandomOrder() {
        Order order = new Order();
        order.setAccountId(Math.abs(random.nextLong()));
        order.setGoodsId(Math.abs(random.nextLong()));
        order.setPayAmount(Math.abs(random.nextInt()));
        order.setPayStatus(new Integer(Math.abs(random.nextInt(127))).byteValue());
        order.setPayTime(new Date(random.nextLong()));
        order.setRefundTime(new Date(random.nextLong()));
        order.setCreateTime(new Date(random.nextLong()));
        order.setUpdateTime(new Date(random.nextLong()));
        return order;
    }

    public static void main(String[] args) {
        System.out.println(getRandomOrder());
    }
}
