package com.lilike.demo.homework01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lilike.demo.homework01.entity.TtOrder;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-12-08
 */
public interface TtOrderMapper extends BaseMapper<TtOrder> {

    int insertData(@Param("bean") TtOrder ttOrder);

}
