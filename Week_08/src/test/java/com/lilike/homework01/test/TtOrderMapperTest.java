package com.lilike.homework01.test;

import com.lilike.demo.homework01.demo.Order;
import com.lilike.demo.homework01.demo.OrderDataGenerator;
import com.lilike.demo.homework01.entity.TtOrder;
import com.lilike.demo.homework01.mapper.TtOrderMapper;
import com.lilike.demo.homework01.utils.OrderIdUtils;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Author llk
 * @Date 2020/12/8 11:15
 * @Version 1.0
 */
public class TtOrderMapperTest extends BaseTest {

    @Autowired
    private TtOrderMapper ttOrderMapper;

    // todo 目前插入 删除 更新都是32,也就是雪花算法算出来了,但是插入到所有的表里面了
    @Test
    public void testInsert() {
        TtOrder ttOrder = new TtOrder();
        Order randomOrder = OrderDataGenerator.getRandomOrder();
        BeanUtils.copyProperties(randomOrder, ttOrder);
        ttOrder.setPayStatus(1);
        ttOrder.setPayTime(new Date());
        ttOrder.setRefundTime(new Date());
        ttOrder.setUpdateTime(new Date());
        ttOrder.setCreateTime(new Date());
        ttOrderMapper.insertData(ttOrder);
    }

    @Test
    public void testSelect() {
        List<TtOrder> ttOrders = ttOrderMapper.selectList(null);
        for (TtOrder ttOrder : ttOrders) {
            System.out.println(ttOrder);
        }
    }

    @Test
    public void testDelete() {
        int i = ttOrderMapper.deleteById(543076852911550464L);
        System.out.println(i);
    }

    @Test
    public void testUpdate() {
        TtOrder ttOrder = new TtOrder();
        ttOrder.setId(543087757942566912L);
        ttOrder.setPayAmount(999);
        ttOrderMapper.updateById(ttOrder);
    }

}
