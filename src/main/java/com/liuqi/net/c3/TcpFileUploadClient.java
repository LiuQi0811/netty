package com.liuqi.net.c3;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/*
 *@ClassName TcpFileUploadClient
 *@Description TcpFileUploadClient 文件传输
 *@Author LiuQi
 *@Date 2023/2/9 16:04
 *@Version 1.0
 */
@Slf4j
public class TcpFileUploadClient
{
    @SneakyThrows
    public static void main(String[] args) {
        Socket socket = null;
        OutputStream os = null;
        FileInputStream fis = null;
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
              // 创建socket 客户端连接
              socket = new Socket(InetAddress.getLocalHost(), 9091);
              // 创建输出流
              os = socket.getOutputStream();
              // 创建 文件输入流 读取文件
              fis = new FileInputStream(new File("WechatIMG9336.jpeg"));
              byte[] buffer = new byte[1024];
              int len;
              while ((len=fis.read(buffer))!=-1){
                os.write(buffer,0,len);
              }
              // 通知服务器我已经传输数据完毕
              socket.shutdownOutput();
              // 确定服务器接收数据完毕，关闭客户端连接
              is = socket.getInputStream();
              bos = new ByteArrayOutputStream();
              byte[] buf = new byte[1024];
              int size;
              if ((size=is.read(buf))!=-1) {
                    bos.write(buf,0,size);
              }
              log.info("{}",bos);


        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(bos!=null){
                bos.close();
            }
            if(is!=null){
                is.close();
            }
            if (os!=null) {
                os.close();
            }
            if (fis!=null) {
                fis.close();
            }
            if (socket!=null) {
                socket.close();
            }
        }
    }
}
