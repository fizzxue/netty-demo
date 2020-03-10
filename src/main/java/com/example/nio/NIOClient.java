package com.example.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Fizz
 */
public class NIOClient {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(6666));
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("等待事件！");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext(); ) {
                SelectionKey next = iterator.next();
                if (next.isConnectable()) {
                    SocketChannel selectableChannel = (SocketChannel) next.channel();
                    if (selectableChannel.finishConnect()) {
                        selectableChannel.write(ByteBuffer.wrap("哈喽，我是新的客户端，哈哈！".getBytes()));
                    }
                }
                iterator.remove();
            }

        }
    }
}
