package io.github.kimmking.gateway.outbound.homework01;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.xml.bind.annotation.XmlType;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Author llk
 * @Date 2020/11/3 21:50
 * @Version 1.0
 */
public class MyHttpNettyClient extends ChannelInboundHandlerAdapter {

    private String proxyServer;

    public MyHttpNettyClient(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        FullHttpRequest request = new DefaultFullHttpRequest(HTTP_1_1, HttpMethod.GET, "/api/hello");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(proxyServer + request.uri());
        CloseableHttpResponse response = httpclient.execute(httpGet);
        FullHttpResponse res = null;
        try {
            String s = EntityUtils.toString(response.getEntity(), "UTF-8");
            s = "lilike " + s;
            System.out.println(s);
        } finally {
            response.close();
            super.channelActive(ctx);
        }
    }


}
