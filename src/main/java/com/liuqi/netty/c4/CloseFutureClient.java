package com.liuqi.netty.c4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/*
 *@ClassName CloseFutureClient
 *@Description CloseFutureClient
 *@Author LiuQi
 *@Date 2023/2/11 19:45
 *@Version 1.0
 */
@Slf4j
public class CloseFutureClient {
    public static void main(String[] args) throws InterruptedException {
        // 创建NioEventLoopGroup对象
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()// 客户端启动器 负责组装 netty组件 启动客户端
                .group(nioEventLoopGroup) // 添加 EventLoopGroup
                .channel(NioSocketChannel.class) // 选择客户端channel 实现
                .handler(new ChannelInitializer<NioSocketChannel>() {  // 添加处理器
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder()); // 创建编码器
                    }
                })
                // 连接服务器
                // 异步非阻塞，main发起了调用，真正执行connect 是 nio线程
                .connect(new InetSocketAddress(8080));
                log.info("客户端已连接服务，请输入发送内容：");

            Channel channel = channelFuture.sync().channel();
            new Thread(()->{
                Scanner  scanner = new Scanner(System.in);
                while (true){
                    final String nextLine = scanner.nextLine();
                    if("q".equals(nextLine)){ // 关闭
                        channel.close(); // close 异步操作 1s 之后
                        break;
                    }
                    // 发送数据
                    channel.writeAndFlush(nextLine);
                }
            },"IN").start();

            // 获取 CloseFuture对象
            ChannelFuture closeFuture = channel.closeFuture();
//            log.info("等待 关闭操作");
//            closeFuture.sync();
            closeFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    log.info("关闭操作");
                    nioEventLoopGroup.shutdownGracefully(); // 关闭nio线程
                }
            });


    }
}
