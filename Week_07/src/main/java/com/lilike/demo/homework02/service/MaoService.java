package com.lilike.demo.homework02.service;

import com.lilike.demo.homework02.entity.Mao;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2020-12-02
 */
public interface MaoService extends IService<Mao> {

    void insert(Integer i);

    Mao read(Integer i);
}
