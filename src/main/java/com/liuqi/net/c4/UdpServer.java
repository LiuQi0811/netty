package com.liuqi.net.c4;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/*
 *@ClassName UdpServer
 *@Description UdpServer
 *@Author LiuQi
 *@Date 2023/2/10 09:23
 *@Version 1.0
 */
@Slf4j
public class UdpServer {
    @SneakyThrows
    public static void main(String[] args) throws SocketException {
        // 还是要等待客户端的连接
        // 开放端口
        final DatagramSocket datagramSocket = new DatagramSocket(9092);
        // 缓冲区
        byte [] buffer = new byte[1024];
        // 接收数据包
        final DatagramPacket datagramPacket = new DatagramPacket(buffer,0,buffer.length);
        // 接收数据 阻塞接收
        datagramSocket.receive(datagramPacket);
        log.info("接收包：{}",datagramPacket.getAddress());
        log.info("接收包内容：{}",new String(datagramPacket.getData(),0,datagramPacket.getLength()));
        // 关闭连接
        datagramSocket.close();


    }
}
