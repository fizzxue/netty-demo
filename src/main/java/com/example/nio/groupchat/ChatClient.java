package com.example.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 群聊服务端:
 * 1.向服务端发送消息
 */
public class ChatClient {

    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        System.out.println(socketChannel.getLocalAddress().toString().substring(1) + "is online!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (selector.select() > 0) {
                            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                            for (; iterator.hasNext(); ) {
                                SelectionKey next = iterator.next();
                                if (next.isReadable()) {
                                    SocketChannel read = (SocketChannel) next.channel();
                                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                                    int readBytes = read.read(allocate);
                                    if (readBytes > 0) {
                                        System.out.println(String.format("%s在线bb%s", socketChannel.getRemoteAddress(),
                                                new String(allocate.array(), 0, readBytes)));
                                    }
                                }
                                iterator.remove();
                            }
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String next = scanner.nextLine();
            if (socketChannel.finishConnect()) {
                socketChannel.write(ByteBuffer.wrap(next.getBytes()));
                System.out.println("发送成功");
            }
        }
    }
}
