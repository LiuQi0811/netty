package com.liuqi.netty.c3;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/*
 *@ClassName ThreadClient
 *@Description TODO
 *@Author LiuQi
 *@Date 2023/2/6 20:44
 *@Version 1.0
 */
@Slf4j
public class ThreadClient {

    public static void main(String[] args) throws IOException {
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8080));
        log.info("正在连接客户端......");
        socketChannel.write(Charset.defaultCharset().encode("王老先生有块地，咿呀咿呀哟！"));
        System.in.read();

    }
}
