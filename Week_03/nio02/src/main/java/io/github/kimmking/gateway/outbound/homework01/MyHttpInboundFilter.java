package io.github.kimmking.gateway.outbound.homework01;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

/**
 * 实现过滤器的功能并添加一个请求头
 * @Author llk
 * @Date 2020/11/3 20:29
 * @Version 1.0
 */
public class MyHttpInboundFilter extends ChannelInboundHandlerAdapter implements HttpRequestFilter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        FullHttpRequest request = (FullHttpRequest) msg;
        filter(request,ctx);
    }

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        HttpHeaders headers = fullRequest.headers();
        headers.add("nio","lilike");
        ctx.fireChannelRead(fullRequest);
    }
}
