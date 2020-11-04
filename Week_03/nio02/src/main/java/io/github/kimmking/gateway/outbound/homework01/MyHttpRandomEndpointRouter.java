package io.github.kimmking.gateway.outbound.homework01;

import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 实现路由的分发
 * 随机算法
 *
 * @Author llk
 * @Date 2020/11/4 15:22
 * @Version 1.0
 */
public class MyHttpRandomEndpointRouter extends ChannelInboundHandlerAdapter implements HttpEndpointRouter {

    // 格式是 http://127.0.0.1:8080;http://127.0.0.1:8081;http://127.0.0.1:8082
    private String proxyServer;
    private final Random random = new Random();

    public MyHttpRandomEndpointRouter(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullRequest = (FullHttpRequest) msg;
        HttpHeaders headers = fullRequest.headers();
        // 具体访问路由藏在header里面
        if (proxyServer.contains(";")) {
        String realProxy = route(Arrays.asList(proxyServer.split(";")));
            headers.add("realProxy",realProxy);
        }else {
            headers.add("realProxy",proxyServer);
        }
        ctx.fireChannelRead(msg);
    }


    @Override
    public String route(List<String> endpoints) {
        int size = endpoints.size();
        return endpoints.get(new Double(Math.floor(random.nextDouble() * size)).intValue());
    }
}
