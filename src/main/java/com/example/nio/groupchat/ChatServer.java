package com.example.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 群聊服务端：
 * 1.客户端上线下线提醒
 * 2.接收客户端消息并群发
 */
public class ChatServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (selector.select(2000) > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                for (; iterator.hasNext(); ) {
                    SelectionKey next = iterator.next();
                    if (next.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println(String.format("有新的用户登录：%s", socketChannel.getRemoteAddress()));
                    } else if (next.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) next.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        SocketAddress remoteAddress = socketChannel.getRemoteAddress();
                        int read = 0;
                        try {
                            read = socketChannel.read(byteBuffer);
                        } catch (IOException e) {
                            System.out.println(String.format("用户%s下线了！", remoteAddress));
                            e.printStackTrace();
                            next.cancel();
                            socketChannel.close();
                            continue;
                        }
                        System.out.println(String.format("%s[在线bb]：%s", remoteAddress, new String(byteBuffer.array(), 0, read)));
                        Set<SelectionKey> keys = selector.keys();
                        Iterator<SelectionKey> keyIterator = keys.iterator();
                        for (; keyIterator.hasNext(); ) {
                            SelectionKey key = keyIterator.next();
                            SelectableChannel channel = key.channel();
                            if (channel instanceof SocketChannel && channel != socketChannel) {
                                SocketChannel write = (SocketChannel) channel;
                                write.write(ByteBuffer.wrap(String.format("%s[在线bb]：%s", remoteAddress,
                                        new String(byteBuffer.array(), 0, read)).getBytes()));
                            }
                        }
                    }
                    iterator.remove();
                }
            }
        }
    }
}
