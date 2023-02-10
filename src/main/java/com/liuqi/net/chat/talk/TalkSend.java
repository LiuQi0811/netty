package com.liuqi.net.chat.talk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/*
 *@ClassName TalkSend
 *@Description TalkSend
 *@Author LiuQi
 *@Date 2023/2/10 12:00
 *@Version 1.0
 */
@Slf4j
public class TalkSend  implements Runnable{
    DatagramSocket socket = null;
    BufferedReader reader = null;
    // 发送方 端口号
    private int fromPort;
    private String toIP;
    private int toPort;

    public TalkSend(int fromPort,String toIP,int toPort){
        this.fromPort = fromPort;
        this.toIP = toIP;
        this.toPort = toPort;
        try {
            // 创建 socket连接
            socket = new DatagramSocket(fromPort);
            // 控制台读取 System.in
            reader = new BufferedReader(new InputStreamReader(System.in));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true){
            try {
                String data = reader.readLine();
                byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
                // 创建包
                DatagramPacket packet = new DatagramPacket(bytes,0,bytes.length,new InetSocketAddress(this.toIP,this.toPort));
                // 发送数据包
                socket.send(packet);
                if (data.equals("bye"))
                {
                    break;
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        // 关闭连接
        socket.close();
    }
}
