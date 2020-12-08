package com.lilike.demo.homework01.demo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author llk
 * @Date 2020/12/2 14:26
 * @Version 1.0
 */
@ToString
@Data
public class Order {

    private Long accountId;

    private Long goodsId;

    private Integer payAmount;

    private Byte payStatus;

    private Date payTime;

    private Date refundTime;

    private Date createTime;

    private Date updateTime;

}
