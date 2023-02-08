package com.liuqi.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/*
 *@ClassName HelloClient
 *@Description HelloClient
 *@Author LiuQi
 *@Date 2023/2/8 14:43
 *@Version 1.0
 */
@Slf4j
public abstract class HelloClient
{
    public static void main(String[] args) throws InterruptedException {
        // 客户端启动器 负责组装 netty组件 启动客户端
        new Bootstrap()
                // 添加 EventLoopGroup
                .group(new NioEventLoopGroup())
                // 选择客户端channel 实现
                .channel(NioSocketChannel.class)
                // 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() { //在建立连接后 被调用
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 创建编码器
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                // 连接到服务器
                .connect(new InetSocketAddress("localhost",8080))
                .sync()
                .channel()
                // 向服务器发送数据
                .writeAndFlush("小妹妹，你好啊");
    }
}
