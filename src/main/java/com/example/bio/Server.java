package com.example.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Fizz
 */
public class Server {
    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket(6666);
            Socket accept = serverSocket.accept();
            System.out.println("有新的客户端1连接！");
            InputStream inputStream = accept.getInputStream();
            inputStream.read();
            Socket accept1 = serverSocket.accept();
            System.out.println("有新的客户端2连接！");
            /*int i = 0;
            while (true) {
                i++;
                Socket accept = serverSocket.accept();
                System.out.println("有新的客户端连接！");
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        InputStream inputStream = null;
                        try {
                            inputStream = accept.getInputStream();
                            int read = -1;
                            byte[] bytes = new byte[1024];
                            while ((read = inputStream.read(bytes)) > -1) {
                                System.out.println(new String(bytes, 0, read));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            System.out.println("客户端退出！");
                        }
                    }
                });
                if (i == 2) {
                    break;
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("开始关闭服务器！");
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("服务器关闭发生异常！");
                e.printStackTrace();
            } finally {
                System.out.println("服务器已关闭！");
            }
        }
    }
}
