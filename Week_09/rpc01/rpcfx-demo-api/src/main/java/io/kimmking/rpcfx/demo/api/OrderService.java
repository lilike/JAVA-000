package io.kimmking.rpcfx.demo.api;

import annotation.MyFeignClient;

@MyFeignClient
public interface OrderService {

    Order findOrderById(int id);

}
