package io.kimmking.rpcfx.api.impl;

import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.RpcfxRequest;

/**
 * @Author llk
 * @Date 2020/12/25 13:54
 * @Version 1.0
 */
public class StandardFilter implements Filter {

    @Override
    public boolean filter(RpcfxRequest request) {
        return true;
    }
}
