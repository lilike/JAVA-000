package com.lilike.netty;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @Author llk
 * @Date 2020/10/27 20:06
 * @Version 1.0
 */
public class Client {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",6666));
        socketChannel.configureBlocking(false);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            socketChannel.write(ByteBuffer.wrap(s.getBytes()));
        }


    }

}
