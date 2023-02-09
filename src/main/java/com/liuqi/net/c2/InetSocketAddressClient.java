package com.liuqi.net.c2;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/*
 *@ClassName InetSocketAddressClient
 *@Description InetSocketAddressClient
 *@Author LiuQi
 *@Date 2023/2/9 14:33
 *@Version 1.0
 */
@Slf4j
public class InetSocketAddressClient {
    @SneakyThrows
    public static void main(String[] args) {
        Socket socket = null;
        OutputStream os= null;
       try {
           // 创建客户端
           socket = new Socket("127.0.0.1",9090);
           // 发送消息 io输出流
           os = socket.getOutputStream();
           os.write("你好 李秀珍".getBytes(StandardCharsets.UTF_8));
           log.info("客户端 {}",socket);
       }catch (IOException e){
           e.printStackTrace();
       }finally {
           // 关闭资源
           if (os!=null){
               os.close();
           }
           if (socket!=null){
               socket.close();
           }
       }
    }
}
