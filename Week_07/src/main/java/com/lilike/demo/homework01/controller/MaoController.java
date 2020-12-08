package com.lilike.demo.homework01.controller;


import com.lilike.demo.homework02.entity.Mao;
import com.lilike.demo.homework02.service.MaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-12-02
 */
@RestController
@Slf4j
@RequestMapping("/mao")
public class MaoController {

    @Autowired
    private MaoService maoService;

    @RequestMapping(value = "/insert")
    public void insert(@RequestParam(value = "maoId")Integer id) {
        maoService.insert(id);
    }

    @RequestMapping(value = "/read")
    public Mao read(@RequestParam(value = "maoId")Integer id) {
        return maoService.read(id);
    }

}
