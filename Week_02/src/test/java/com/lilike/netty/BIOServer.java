package com.lilike.netty;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static sun.misc.Signal.handle;

/**
 * @Author llk
 * @Date 2020/10/27 21:48
 * @Version 1.0
 */
public class BIOServer {

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(6666);
        while (true)  {
            System.out.println("等待连接......");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端...");
            pool.execute(new Runnable() {
                public void run() {
                    try {
                        handle(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private static void handle(Socket socket) throws IOException {
        System.out.println("线程信息" + Thread.currentThread().getName());
        byte[] bytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();

        while (true) {
            int read = inputStream.read(bytes);
            if (read != -1) {
                System.out.println(new String(bytes,0,read));
            }else {
                break;
            }
        }
    }
}
