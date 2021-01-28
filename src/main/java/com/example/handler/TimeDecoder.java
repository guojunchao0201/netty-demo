package com.example.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author guojunchao
 */
public class TimeDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        //If decode() adds an object to list, it means the decoder decoded a message successfully.
        //ByteToMessageDecoder will discard the read part of the cumulative buffer.
        list.add(byteBuf.readBytes(4));
    }
}
