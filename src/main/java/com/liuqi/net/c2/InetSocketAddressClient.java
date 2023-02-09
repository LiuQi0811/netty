package com.liuqi.net.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

/*
 *@ClassName InetSocketAddressClient
 *@Description InetSocketAddressClient
 *@Author LiuQi
 *@Date 2023/2/9 14:33
 *@Version 1.0
 */
@Slf4j
public class InetSocketAddressClient {
    public static void main(String[] args) {
       try {
           // 创建客户端
           final Socket socket = new Socket("127.0.0.1",9090);
           log.info("客户端 {}",socket);
       }catch (IOException e){
           e.printStackTrace();
       }
    }
}
