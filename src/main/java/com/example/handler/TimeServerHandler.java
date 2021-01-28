package com.example.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author guojunchao
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

//    invoked when a connection is established and ready to generate traffic.
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
//         Get the current ByteBufAllocator via ChannelHandlerContext.alloc() and allocate a new buffer.
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        //ctx.writeAndFlush()方法返回ChannelFuture,因为netty中所有操作都是异步的,表示此i/o操作maybe还没有执行完
        //因此需要通过添加监听器监听该操作完成
        //  ==f.addListener(ChannelFutureListener.CLOSE);
        final ChannelFuture f = ctx.writeAndFlush(time);
//        f.addListener(new ChannelFutureListener() {
//            public void operationComplete(ChannelFuture future) {
//                assert f == future;
//                ctx.close();
//            }
//        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer(4).writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
