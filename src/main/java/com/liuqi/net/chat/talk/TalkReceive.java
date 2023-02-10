package com.liuqi.net.chat.talk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/*
 *@ClassName TalkReceive
 *@Description TalkReceive
 *@Author LiuQi
 *@Date 2023/2/10 12:15
 *@Version 1.0
 */
@Slf4j
public class TalkReceive implements Runnable{

    DatagramSocket socket = null;
    DatagramPacket packet = null;
    private int port;
    private String fromMsg;
    @SneakyThrows
    public TalkReceive(int port,String fromMsg){
        this.port = port;
        this.fromMsg = fromMsg;
        // 创建 socket连接
        socket = new DatagramSocket(port);


    }
    @SneakyThrows
    @Override
    public void run() {

        while (true){
            // 创建包
            byte[] buffer = new byte[1024];
            packet = new DatagramPacket(buffer,0,buffer.length);
            //接收包数据 阻塞式接收
            socket.receive(packet);
            // 断开连接 bye
            byte[] data = packet.getData();
            String receiveData = new String(data, 0, packet.getLength());
            if(receiveData.equals("bye")){
                break;
            }
            log.info("{}:{}",packet.getAddress(),packet.getPort());
            log.info(this.fromMsg+ "：{}",receiveData);

        }
        // 关闭连接
        socket.close();
    }
}
