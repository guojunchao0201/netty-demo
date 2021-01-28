package com.example.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author guojunchao
 */
public class DisacrdServerHandler extends ChannelInboundHandlerAdapter {

    //接收到新消息时调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
//        ((ByteBuf) msg).release();

//        ByteBuf in = (ByteBuf) msg;

        //通常情况下
//        try {
//            //do something
//            while (in.isReadable()) {
//                System.out.println((char) in.readByte());
//                System.out.flush();
//            }
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
        ctx.write(msg);
        ctx.flush();
//        ctx.writeAndFlush(msg);
    }

    //异常时触发
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
