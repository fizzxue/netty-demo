package com.example.netty.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author Fizz
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        ByteBuf byteBuf = (ByteBuf) msg;
//        System.out.println(String.format("收到客户端消息：%s", byteBuf.toString(CharsetUtil.UTF_8)));
        //自定义普通任务，多任务在同一线程顺序执行；自定义定时任务，多任务在同一线程顺序执行，定时开始时间相同。
/*        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    System.out.println("thread id=================="+Thread.currentThread().getId());
                    System.out.println(System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20 * 1000);
                    System.out.println("thread id=================="+Thread.currentThread().getId());
                    System.out.println(System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });*/
/*        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread id=================="+Thread.currentThread().getId());
                System.out.println(System.currentTimeMillis());

            }
        }, 10, TimeUnit.SECONDS);
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread id=================="+Thread.currentThread().getId());
                System.out.println(System.currentTimeMillis());
            }
        }, 20, TimeUnit.SECONDS);*/
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("客户端，你好", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
