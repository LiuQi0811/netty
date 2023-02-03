package com.liuqi.netty.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/*
 *@ClassName Server
 *@Description 服务端
 *@Author LiuQi
 *@Date 2023/2/3 16:53
 *@Version 1.0
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        socketChannelBlocking();
    }

    /**
     * 服务
     * 使用 nio 理解阻塞模式，单线程
     *
     * @throws IOException
     */
    private static void socketChannelBlocking() throws IOException {
        // 创建缓冲区
        final ByteBuffer byteBuffer = ByteBuffer.allocate(30);
        // 创建 server socket 服务器
        final ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 绑定监听端口
        socketChannel.bind(new InetSocketAddress("localhost", 8080));

        // 创建连接集合
        final List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            log.info("开始创建服务连接中.......");
            // 创建服务连接  accept建立与客户端连接 tcp 三次握手  SocketChannel用来与客户端之间通信
            final SocketChannel socket = socketChannel.accept(); //阻塞方法，线程停止运行 空闲状态
            log.info("创建服务结束....... {}", socket);
            channels.add(socket);
            for (SocketChannel channel : channels) {
                log.info("数据开始读取中...... {}", channel);
                // 接收客户端发送的消息
                final int read = channel.read(byteBuffer);//阻塞方法，线程停止运行 空闲状态
                log.info("read 读取字节数量： {}", read);
                // 切换读模式
                byteBuffer.flip();
                if (byteBuffer.hasRemaining()) { //剩余未读数据
                    log.info("{}", Charset.defaultCharset().decode(byteBuffer));
                }
                // 切换写模式
                byteBuffer.clear();
                log.info("数据读取结束......  {}", channel);
            }


        }
    }


    /**
     * 服务
     * 使用 nio 理解非阻塞模式，单线程
     *
     * @throws IOException
     */
    private static void socketChannelNoBlocking() throws IOException {
        // 创建缓冲区
        final ByteBuffer byteBuffer = ByteBuffer.allocate(30);
        // 创建 server socket 服务器
        final ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 设置非阻塞模式
        socketChannel.configureBlocking(false);
        // 绑定监听端口
        socketChannel.bind(new InetSocketAddress( 8080));

        // 创建连接集合
        final List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            log.info("开始创建服务连接中.......");
            // 创建服务连接  accept建立与客户端连接 tcp 三次握手  SocketChannel用来与客户端之间通信
            final SocketChannel socket = socketChannel.accept(); //非阻塞方法，线程会继续运行,如果没有连接建立socket 为null
            if(socket != null){ // 创建服务连接不为null
                log.info("创建服务结束....... {}", socket);
                // 设置非阻塞模式
                socket.configureBlocking(false);
                channels.add(socket);
            }
            for (SocketChannel channel : channels) {
                log.info("数据开始读取中...... {}", channel);
                // 接收客户端发送的消息
                final int read = channel.read(byteBuffer);//非阻塞方法，线程会继续运行，如果没有读到数据read返回0
                log.info("read 读取字节数量： {}", read);
                if(read > 0){ //read 大于 0 读取数据
                    // 切换读模式
                    byteBuffer.flip();
                    if (byteBuffer.hasRemaining()) { //剩余未读数据
                        log.info("{}", Charset.defaultCharset().decode(byteBuffer));
                    }
                    // 切换写模式
                    byteBuffer.clear();
                    log.info("数据读取结束......  {}", channel);
                }
            }



        }
    }
}
