package com.liuqi.net.c3;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/*
 *@ClassName TcpFileUploadServer
 *@Description TcpFileUploadServer 文件接收
 *@Author LiuQi
 *@Date 2023/2/9 16:17
 *@Version 1.0
 */
@Slf4j
public class TcpFileUploadServer {
    @SneakyThrows
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream is = null;
        FileOutputStream fos = null;
        OutputStream os = null;
       try {
           //创建服务
           serverSocket = new ServerSocket(9091);
           // 监听客户端的连接 阻塞式监听，会一直等待客户端连接
           socket = serverSocket.accept();
           // 获取输入流
           is = socket.getInputStream();
           // 文件输出
           fos = new FileOutputStream(new File("xiaoxue.jpeg"));
           // 创建缓冲区
           byte[] buffer = new byte[1024];
           int len;
           while ((len=is.read(buffer))!=-1) {
               fos.write(buffer,0,len);
           }
           // 通知客户端我接受完毕了
           os = socket.getOutputStream();
           os.write("服务端数据接收完毕，请断开连接！".getBytes(StandardCharsets.UTF_8));
       }catch (IOException e){
           e.printStackTrace();
       }finally {
           if (fos!=null) {
               fos.close();
           }
           if (is!=null) {
               is.close();
           }
           if (socket!=null) {
               socket.close();
           }
           if (serverSocket!=null) {
               serverSocket.close();
           }
       }



    }
}
