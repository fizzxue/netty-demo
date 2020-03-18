package com.example.netty.codec.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author Fizz
 */
public class ServerEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        System.out.println("server encoder");
        out.writeCharSequence(msg, CharsetUtil.UTF_8);
    }
}
