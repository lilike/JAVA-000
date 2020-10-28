package com.lilike.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author llk
 * @Date 2020/10/27 19:56
 * @Version 1.0
 */
public class SockerServer01 {


    public static void main(String[] args) throws Exception {

        //  创建通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int select = selector.select();
            if (select > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    if (next.isAcceptable()) {
                        SocketChannel channel = serverSocketChannel.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                        System.out.println("某人上线了");
                    } else if (next.isReadable()) {
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        try{
                            channel.read(byteBuffer);
                        }catch (IOException ex) {
                            System.out.println("某人下线了!");
                            next.cancel();
                            channel.close();
                        }
                        System.out.println(new String(byteBuffer.array()));

                    }
                    iterator.remove();
                }

            }


        }


    }


}
