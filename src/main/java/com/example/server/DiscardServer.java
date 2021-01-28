package com.example.server;

import com.example.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author guojunchao
 */
public class DiscardServer {

    private final int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {

        //a multithreaded event loop that handles I/O operation
        //accepts an incoming connection.
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        //handles the traffic of the accepted connection once the boss accepts the connection
        // and registers the accepted connection to the worker
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //set up the server
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    //instantiate a new Channel to accept incoming connections
                    .channel(NioServerSocketChannel.class)
                    // The ChannelInitializer is a special handler that is purposed to help a user configure a new Channel.
                    // It is most likely that you want to configure
                    // the ChannelPipeline of the new Channel by adding some handlers
                    // such as DiscardServerHandler to implement your network application.
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeServerHandler());
                        }
                    })
                    //the parameters which are specific to the Channel implementation
                    //option() is for the NioServerSocketChannel that accepts incoming connections.
                    // childOption() is for the Channels accepted by the parent ServerChannel,
                    // which is NioSocketChannel in this case.
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture f = b.bind(port).sync();
//            f.channel().closeFuture().sync();
        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new DiscardServer(port).run();
    }
}
