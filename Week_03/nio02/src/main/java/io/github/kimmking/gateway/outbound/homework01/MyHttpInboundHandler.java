package io.github.kimmking.gateway.outbound.homework01;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Author llk
 * @Date 2020/11/3 20:19
 * @Version 1.0
 */
public class MyHttpInboundHandler extends ChannelInboundHandlerAdapter {

    private String proxyServer;

    public MyHttpInboundHandler() {
    }

    public MyHttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        if (request.headers().get("realProxy") != null && !"".equals(request.headers().get("realProxy"))) {
            proxyServer = request.headers().get("realProxy");
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(proxyServer + request.uri());
        CloseableHttpResponse response = httpclient.execute(httpGet);
        FullHttpResponse res = null;
        try {
            String s = EntityUtils.toString(response.getEntity(), "UTF-8");
            s = "lilike " + s + " " + proxyServer;
            res = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(s.getBytes()));
            res.headers().set("Content-Type", "application/json");
            res.headers().setInt("Content-Length", s.length());
        } finally {
            response.close();
            ctx.write(res);
            ctx.flush();
        }


    }
}
