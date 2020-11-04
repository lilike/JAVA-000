package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.outbound.homework01.MyHttpInboundFilter;
import io.github.kimmking.gateway.outbound.homework01.MyHttpInboundHandler;
import io.github.kimmking.gateway.outbound.homework01.MyHttpRandomEndpointRouter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
	
	private String proxyServer;
	
	public HttpInboundInitializer(String proxyServer) {
		this.proxyServer = proxyServer;
	}
	
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
		p.addLast(new HttpServerCodec());
		//p.addLast(new HttpServerExpectContinueHandler());
		p.addLast(new HttpObjectAggregator(1024 * 1024));
		p.addLast(new MyHttpInboundFilter());
		p.addLast(new MyHttpRandomEndpointRouter(proxyServer));
		p.addLast(new MyHttpInboundHandler());
	}
}
