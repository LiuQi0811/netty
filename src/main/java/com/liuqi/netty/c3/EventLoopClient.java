package com.liuqi.netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/*
 *@ClassName EventLoopClient
 *@Description EventLoopClient
 *@Author LiuQi
 *@Date 2023/2/8 20:06
 *@Version 1.0
 */
@Slf4j
public abstract class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        methodTwo();
    }
    /**
     * 发送消息方式一
     * @throws InterruptedException
     */
    private static void methodOne() throws InterruptedException {
        final Scanner scanner = new Scanner(System.in);
        do {
            log.info("请输入想要发送的信息：    退出请输入：exit");
            final String message = scanner.nextLine();
            if(message.equals("exit")){
                log.info("退出成功！");
                break;
            }
            init().sync() // sync方法同步处理结果
                    .channel()
                    .writeAndFlush(message);
        }while (true);
    }

    /**
     * 发送消息方式二
     * @throws InterruptedException
     */
    private static void methodTwo() throws InterruptedException {
        init().
                // 使用addListener(回调对象) 方法异步处理结果
                addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception { //在 nio线程连接建立好之后，会调用
                        Channel channel = channelFuture.channel();
                        channel.writeAndFlush("你好啊 陌生人");

                    }
                });
    }
    /**
     * 客户端启动器 负责组装 netty组件 启动客户端
     * @return
     */
    private static ChannelFuture init() throws InterruptedException {
        // 客户端启动器 负责组装 netty组件 启动客户端
        return new Bootstrap()
                // 添加 EventLoopGroup
                .group(new NioEventLoopGroup())
                // 选择客户端channel 实现
                .channel(NioSocketChannel.class)
                // 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 创建编码器
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                // 连接服务器
                // 异步非阻塞，main发起了调用，真正执行connect 是 nio线程
                .connect(new InetSocketAddress(8080));
    }
}
