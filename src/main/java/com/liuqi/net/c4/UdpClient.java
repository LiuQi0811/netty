package com.liuqi.net.c4;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/*
 *@ClassName UdpClient
 *@Description UdpClient
 *@Author LiuQi
 *@Date 2023/2/9 21:30
 *@Version 1.0
 */
@Slf4j
public class UdpClient {
    // 不需要连接服务器
    @SneakyThrows
    public static void main(String[] args) {
        // 建立一个socket连接
        final DatagramSocket socket = new DatagramSocket();
        //本地ip
        final InetAddress localHost = InetAddress.getLocalHost();
        // 端口号
        int port = 9092;
        // 消息内容
        String message = "你好啊 小美眉";
        // 创建个包
        final DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(StandardCharsets.UTF_8), 0, message.getBytes(StandardCharsets.UTF_8).length, localHost, port);
        // 发送包
        socket.send(datagramPacket);
        // 关闭流
        socket.close();


    }
}
