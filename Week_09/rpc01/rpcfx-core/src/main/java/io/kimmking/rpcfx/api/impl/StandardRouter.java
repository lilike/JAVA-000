package io.kimmking.rpcfx.api.impl;

import io.kimmking.rpcfx.api.Router;

import java.util.List;

/**
 * @Author llk
 * @Date 2020/12/25 10:14
 * @Version 1.0
 */
public class StandardRouter implements Router {
    @Override
    public List<String> route(List<String> urls) {
        return urls;
    }
}
