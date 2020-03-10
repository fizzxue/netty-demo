package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Fizz
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("等待事件！");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("有新的客户端接入！IP：" + socketChannel.socket().getInetAddress().getHostAddress());
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                } else if (next.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) next.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) next.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("收到客户端消息：" + new String(byteBuffer.array()));
                }
                iterator.remove();
            }
        }
    }
}
