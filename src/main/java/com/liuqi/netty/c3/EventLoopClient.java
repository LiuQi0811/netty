package com.liuqi.netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
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
        final Scanner scanner = new Scanner(System.in);
       do {
           log.info("请输入想要发送的信息：    退出请输入：exit");
           final String message = scanner.nextLine();
           if(message.equals("exit")){
               log.info("退出成功！");
            break;
           }
           init().sync()
                   .channel()
                   .writeAndFlush(message);
       }while (true);
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
                .connect(new InetSocketAddress(8080));
    }
}
