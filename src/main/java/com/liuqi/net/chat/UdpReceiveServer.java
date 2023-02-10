package com.liuqi.net.chat;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/*
 *@ClassName UdpReceiveServer
 *@Description UdpReceiveServer
 *@Author LiuQi
 *@Date 2023/2/10 10:23
 *@Version 1.0
 */
@Slf4j
public class UdpReceiveServer {
    @SneakyThrows
    public static void main(String[] args) {
        // 创建 socket连接
        DatagramSocket socket = new DatagramSocket(6666);

        while (true){
            // 创建包
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer,0,buffer.length);
            //接收包数据 阻塞式接收
            socket.receive(packet);
            // 断开连接 bye
            byte[] data = packet.getData();
            String receiveData = new String(data, 0, packet.getLength());
            if(receiveData.equals("bye")){
                break;
            }
            log.info("接收到客户端信息：{}",receiveData);

        }
        // 关闭连接
        socket.close();



    }
}
