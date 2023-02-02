package com.liuqi.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/*
 *@ClassName ByteBufferReader
 *@Description ByteBufferReader 读取
 *@Author LiuQi
 *@Date 2023/2/2 20:13
 *@Version 1.0
 */
@Slf4j
public class ByteBufferReader {
    public static void main(String[] args) {
        reader();
    }

    /**
     * 读取方法
     */
    public static void reader(){
        // 准备缓冲区 allocate划分一块内存作为缓冲区
        final ByteBuffer buffer = ByteBuffer.allocate(10);
        // 添加数据
        buffer.put(new byte[]{'a','b','c','d'});
        // 切换为读模式
        buffer.flip();

        // 获取数据
        if(buffer.hasRemaining()){ // 剩余未读的内容
            // rewind 从头开始读
            buffer.get(new byte[4]);
            buffer.rewind();
            // 打印内容
            log.debug("{}", ((char) buffer.get())); // a
            log.debug("{}", ((char) buffer.get())); // b
            // mark & reset
            // mark 做一个标记,记录position位置，reset 是将position重置到mark的位置
            final Buffer mark = buffer.mark();// 加标记，索引2的位置
            log.debug("加标记索引 {} 位置", mark.position()); // 2
            log.debug("{}", ((char) buffer.get())); // c
            log.debug("{}", ((char) buffer.get())); // d
            final Buffer reset = buffer.reset();
            log.debug("重置到 {} 位置", reset.position()); // 2
            log.debug("{}", ((char) buffer.get())); // c
            log.debug("{}", ((char) buffer.get())); // d
            // get(i) 获取指定位置内容，不会改变读索引的位置
            final char c = (char) buffer.get(3);
            log.debug("((char) buffer.get(3)): {}",c);




        }




    }


}
