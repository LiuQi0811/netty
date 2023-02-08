package com.liuqi.nio.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/*
 *@ClassName Client
 *@Description TODO
 *@Author LiuQi
 *@Date 2023/2/3 17:24
 *@Version 1.0
 */
@Slf4j
public class Client {
    public static void main(String[] args) throws IOException {
        // 创建 socket 服务器
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8080));
        socketChannel.write(Charset.defaultCharset().encode("我爱你，李桂雪"));
        log.info("等待中.....");
        System.in.read(); //阻塞方法

    }
}
