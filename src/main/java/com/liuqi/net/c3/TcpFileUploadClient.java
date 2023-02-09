package com.liuqi.net.c3;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/*
 *@ClassName TcpFileUploadClient
 *@Description TcpFileUploadClient
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
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
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
