package com.liuqi.net.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/*
 *@ClassName InetSocketAddressServer
 *@Description InetSocketAddressServer
 *@Author LiuQi
 *@Date 2023/2/9 14:06
 *@Version 1.0
 */
@Slf4j
public class InetSocketAddressServer {
    public static void main(String[] args) throws IOException {
        server();
    }

    /**
     * 获取地址详情
     */
    private static void getAddressInfo(){
        // 参数一： 本地ip   参数二：端口号
        final InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",8080);
        log.info("「 ip地址：」{}",socketAddress.getAddress());
        log.info("「 ip地址：」{}",socketAddress.getHostName());
        log.info("「 ip地址：」{}",socketAddress.getHostString());
        log.info("「 ip端口号：」{}",socketAddress.getPort());
    }

    private static void server(){
        try {
            // 创建服务端
            final ServerSocket serverSocket = new ServerSocket(9090);
            // 连接服务
            serverSocket.accept();
            while (true){
                // 读取数据
                log.info("服务端 {}",serverSocket);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {

        }

    }
}
