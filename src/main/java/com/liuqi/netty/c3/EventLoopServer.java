package com.liuqi.netty.c3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/*
 *@ClassName EventLoopServer
 *@Description EventLoopServer
 *@Author LiuQi
 *@Date 2023/2/8 19:48
 *@Version 1.0
 */
@Slf4j
public abstract class EventLoopServer {

    public static void main(String[] args) {
        init();
    }
    /**
     * 服务启动器初始化
     * 负责组装 netty组件 启动服务器
     * @return
     */
    public static ChannelFuture init(){
        // 服务启动器 负责组装 netty组件 启动服务器
        return new ServerBootstrap()
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
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { //读事件
                                final ByteBuf buf = (ByteBuf) msg;
                                log.info("buf读取信息 {}", buf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                })
                //绑定 IP，端口
                .bind(8080);
    }
}
