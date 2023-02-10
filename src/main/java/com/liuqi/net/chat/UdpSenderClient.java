package com.liuqi.net.chat;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/*
 *@ClassName UdpSenderClient
 *@Description UdpSenderClient
 *@Author LiuQi
 *@Date 2023/2/10 10:23
 *@Version 1.0
 */
@Slf4j
public class UdpSenderClient {
    @SneakyThrows
    public static void main(String[] args) {
        // 创建 socket连接
         DatagramSocket socket = new DatagramSocket(8888);
        // 控制台读取 System.in
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String data = reader.readLine();
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        // 创建包
        DatagramPacket packet = new DatagramPacket(bytes,0,bytes.length,new InetSocketAddress("localhost",6666));
        // 发送数据包
        socket.send(packet);
        // 关闭连接
        socket.close();

    }
}
