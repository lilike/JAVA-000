package com.lilike.demo.homework02.service.impl;

import com.lilike.demo.homework02.ds.ReadOnly;
import com.lilike.demo.homework02.entity.Mao;
import com.lilike.demo.homework02.mapper.MaoMapper;
import com.lilike.demo.homework02.service.MaoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-12-02
 */
@Slf4j
@Service
public class MaoServiceImpl extends ServiceImpl<MaoMapper, Mao>
        implements MaoService {

    @Autowired
    private MaoMapper maoMapper;

    @Override
    public void insert(Integer i){
        Mao mao = new Mao();
        mao.setId(i);
        maoMapper.insert(mao);
    }

    @ReadOnly
    @Override
    public Mao read(Integer i) {
        Mao mao = maoMapper.selectById(i);
        return mao;
    }
}
