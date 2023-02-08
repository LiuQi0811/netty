package com.liuqi.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/*
 *@ClassName HelloServer
 *@Description HelloServer
 *@Author LiuQi
 *@Date 2023/2/8 12:33
 *@Version 1.0
 */
@Slf4j
public class HelloServer {
    public static void main(String[] args) {
        // 服务启动器 负责组装 netty组件 启动服务器
     new ServerBootstrap()
                // NioEventLoopGroup （selector,thread） 包含选择器和线程 ，group组
                .group(new NioEventLoopGroup())
                // 选择 服务器的ServerSocketChannel 实现
                .channel(NioServerSocketChannel.class)
                // 一个负责处理连接 另一个负责处理读写，决定了 worker(child) 能执行哪些操作（handler）
                .childHandler(
                        //  channel 代表和客户端进行数据读写的通道，Initializer初始化，负责添加别的 handler
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 将 ByteBuf 转换为字符串
                        // 创建解码器
                        nioSocketChannel.pipeline().addLast(new StringDecoder());
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){ //自定义handler
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { // 读事件
                                log.info("INFO{}",msg);
                            }
                        });

                    }
                })
             // 绑定监听端口
             .bind(8080);
    }
}
