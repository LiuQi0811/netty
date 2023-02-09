package com.liuqi.net.c2;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
        server("");
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

    @SneakyThrows
    private static void server(String type){
        ServerSocket serverSocket = null;
        Socket socket =null;
        InputStream is = null;
        ByteArrayOutputStream outputStream =null;
        try {
            // 创建服务端
            serverSocket  = new ServerSocket(9090);
            //等待客户端 连接服务 阻塞模式
            socket = serverSocket.accept();
            log.info("服务端 {}",serverSocket);

                // 读取数据
                // 读取接收客户端消息
                is = socket.getInputStream();
                if(type.equals("1")){
                    // 创建缓冲区
                    byte[] bytes =new byte[1024];
                    int len;
                    // 循环接收数据
                    while ((len = is.read(bytes))!=-1){
                        final String message = new String(bytes, 0, len);
                        log.info("接收到的消息：{}",message);
                    }
                }else {
                    // 创建 io管道流
                    outputStream = new ByteArrayOutputStream();
                    // 创建缓冲区
                    byte[] buffer =new byte[1024];
                    int len;
                    while ((len= is.read(buffer))!=-1){
                        outputStream.write(buffer,0,len);
                    }
                    log.info("接收到的消息：{}",outputStream);

                }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            // 关闭资源
            if (outputStream!=null){
                outputStream.close();
            }
           if (is!=null){
               is.close();
           }
            if (socket!=null){
                socket.close();
            }
            if (serverSocket!=null){
                serverSocket.close();
            }
        }

    }
}
