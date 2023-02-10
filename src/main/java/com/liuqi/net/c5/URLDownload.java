package com.liuqi.net.c5;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 *@ClassName URLDownload
 *@Description URLDownload
 *@Author LiuQi
 *@Date 2023/2/10 15:02
 *@Version 1.0
 */
@Slf4j
public class URLDownload {
    @SneakyThrows
    public URLDownload(String urls){
        URL url = new URL(urls);
        // 协议
        String protocol = url.getProtocol();
        // 主机ip
        String host = url.getHost();
        // 端口
        int port = url.getPort();
        // 文件
        String path = url.getPath();
        // 全路径
        String file = url.getFile();
        // 参数
        String query = url.getQuery();
        log.info("协议: {}，主机ip: {}，端口: {}，文件: {}，全路径: {}，参数: {}",protocol,host,port,path,file,query);
        //连接到这个资源 HTTP
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        // 输入流
        InputStream is = urlConnection.getInputStream();
        // 读取的数据
        FileOutputStream fos = new FileOutputStream("small.png");
        byte[] buffer = new byte[1024];
        int len;
        while ((len=is.read(buffer))!=-1){
            fos.write(buffer,0,len);
        }

        // 关闭资源
        fos.close();
        is.close();
        urlConnection.disconnect();

    }
    public static void main(String[] args) {
        new URLDownload("https://pic.169pp.net/169mm/202207/011/2.jpg");
    }
}
