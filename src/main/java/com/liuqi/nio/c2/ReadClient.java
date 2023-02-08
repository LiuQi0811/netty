package com.liuqi.nio.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/*
 *@ClassName ReadClient
 *@Description ReadClient 客户端读取接收数据
 *@Author LiuQi
 *@Date 2023/2/6 10:27
 *@Version 1.0
 */
@Slf4j
public class ReadClient {
    public static void main(String[] args) throws IOException {
        // 创建server socket 服务
        final SocketChannel socketChannel = SocketChannel.open();
        // 绑定 ip 端口号
        socketChannel.connect(new InetSocketAddress("localhost",8080));
        // 接收数据
        int count = 0;
        ByteBuffer buffer = null;
        while (true){
            buffer = ByteBuffer.allocate(16);
            count  += socketChannel.read(buffer);
            log.info("{}",count);
            buffer.clear();
            log.info("接收到的数据，返回结果 {} ", Charset.defaultCharset().decode(buffer));
        }


    }
}
