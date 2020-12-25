package io.kimmking.rpcfx.api.impl;

import io.kimmking.rpcfx.api.LoadBalancer;

import java.util.List;

/**
 * @Author llk
 * @Date 2020/12/25 10:15
 * @Version 1.0
 */
public class StandardLoadBalancer implements LoadBalancer {

    int i = 0;

    @Override
    public String select(List<String> urls) {


        if (urls.size() == i) {
            i = 0;
        }
        return urls.get(i++);
    }

}
