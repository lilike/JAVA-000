package io.kimmking.rpcfx.demo.api;

import annotation.MyFeignClient;

@MyFeignClient
public interface UserService {

    User findById(int id);

}
