package com.liuqi.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/*
 *@ClassName ByteBufferForString
 *@Description ByteBufferForString
 *@Author LiuQi
 *@Date 2023/2/2 21:15
 *@Version 1.0
 */
@Slf4j
public class ByteBufferForString
{
    public static void main(String[] args) {
        strConvertByteBuffer();
        charsetConvertByteBuffer();
        wrapConvertByteBuffer();
        byteBufferConvertString();
    }

    /**
     * String字符串 转换 ByteBuffer
     */
    public static void strConvertByteBuffer(){
        // 准备缓冲区 allocate划分一块内存作为缓冲区
        final ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes(StandardCharsets.UTF_8));
        // 切换读模式
        buffer.flip();
        while (buffer.hasRemaining()) { //剩余未读的内容
            log.debug("{}", ((char) buffer.get()));
        }
        log.debug("---------------------------------------------");
        log.debug("---------------------------------------------");

    }

    /**
     *  Charset字符集 转换 ByteBuffer
     */
    public static void charsetConvertByteBuffer(){
        // Charset字符集 转换 ByteBuffer准备缓冲区
        final ByteBuffer buffer = StandardCharsets.UTF_8.encode("world");
        // 不需要 切换读模式 自动读取
        while (buffer.hasRemaining()){ //剩余未读的内容
            log.debug("{}",(char)buffer.get());
        }
        log.debug("---------------------------------------------");
        log.debug("---------------------------------------------");
    }

    /**
     *  wrap ByteBuffer
     */
    public static void wrapConvertByteBuffer(){
        // wrap ByteBuffer准备缓冲区
        final ByteBuffer buffer = ByteBuffer.wrap("java".getBytes(StandardCharsets.UTF_8));
        // 不需要 切换读模式 自动读取
        while (buffer.hasRemaining()){ //剩余未读的内容
            log.debug("{}",(char)buffer.get());
        }
        log.debug("---------------------------------------------");
        log.debug("---------------------------------------------");
    }

    /**
     * ByteBuffer 转换 String字符串
     */
    public static void byteBufferConvertString(){
        final ByteBuffer buffer = ByteBuffer.wrap("老鼠爱大米".getBytes(StandardCharsets.UTF_8));
        final String data = StandardCharsets.UTF_8.decode(buffer).toString();
        log.debug("字符串结果： {}",data);

    }
}
