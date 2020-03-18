package com.example.netty.groupchat.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.SocketAddress;

/**
 * @author Fizz
 */
public class NettyGroupChatServerHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup recipients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        System.out.println(String.format("================>>>【%s】上线了", socketAddress));
        recipients.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        System.out.println(String.format("<<<================【%s】下线了", socketAddress));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            SocketAddress socketAddress = ctx.channel().remoteAddress();
            ByteBuf byteBuf = (ByteBuf) msg;
            String sendAllMsg = String.format("=================>>>收到客户端【%s】消息: %s, ", socketAddress, byteBuf.toString(CharsetUtil.UTF_8));
            System.out.println(sendAllMsg);
            this.sendAll(sendAllMsg);
        }
    }

    private void sendAll(String msg) {
        recipients.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
        System.out.println("=================>>>群发消息完毕！");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
