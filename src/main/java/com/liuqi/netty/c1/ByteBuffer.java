package com.liuqi.netty.c1;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/*
 *@ClassName ByteBuffer
 *@Description ByteBuffer 读取数据
 *@Author LiuQi
 *@Date 2023/2/2 17:46
 *@Version 1.0
 */
@Slf4j
public class ByteBuffer {

    public static void main(String[] args) {
        String path = "data.txt";
        readerFile(path);
    }

    /**
     * 读取文件
     */
    public static void readerFile(String file) {
        // 获取 fileChannel ，通过输入输出流或者RandomAccessFile
       try (final FileChannel fileChannel = new FileInputStream(file).getChannel()){
            // 准备缓冲区 allocate划分一块内存作为缓冲区
           java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(10);
           while (true){
               // 从channel 读取数据 向buffer 写入
               final int len = fileChannel.read(buffer);
               log.debug("读取的内容字节码：{}",len);
               if(len==-1){ //如果读取的内容 为 -1 终止跳出循环
                   break;
               }
               // 切换至读模式
               buffer.flip();
               while (buffer.hasRemaining()){ //buffer 是否有值 剩余未读的数据
               //获取 缓冲区数据
               final byte data = buffer.get();
               // 缓冲区数据 转换成char
               final char c = (char) data;
               System.out.println(c);
               }
               // 切换至写模式 如果切换会一只循环读
               buffer.clear();


           }

       }catch (IOException e){
           e.printStackTrace();
       }
    }
}
