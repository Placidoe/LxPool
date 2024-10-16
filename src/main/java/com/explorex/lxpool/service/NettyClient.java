package com.explorex.lxpool.service;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws Exception {
        // 配置客户端
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                  .channel(NioSocketChannel.class)
                  .option(ChannelOption.TCP_NODELAY, true)
                  .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 在这里添加你的处理逻辑
                            ch.pipeline().addLast(new MyClientHandler());
                            ch.writeAndFlush("Hello, I'm a Netty client.");
                        }
                    });

            // 连接到服务器
            ChannelFuture f = b.connect("localhost", 8080).sync();


            // 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            // 关闭线程组
            group.shutdownGracefully();
        }
    }

    public static class MyClientHandler extends SimpleChannelInboundHandler<String> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println("Received message from server: " + msg);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Connected to server: " + ctx.channel().remoteAddress());
            ctx.writeAndFlush("Hello, I'm a Netty client.");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
