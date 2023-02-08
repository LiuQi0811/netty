package com.liuqi.nio.c1;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/*
 *@ClassName ByteBufferAllocate
 *@Description ByteBufferAllocate 分配空间比较
 *@Author LiuQi
 *@Date 2023/2/2 20:02
 *@Version 1.0
 */
@Slf4j
public abstract class ByteBufferAllocate {

    public static void main(String[] args) {
        init();
    }

    /**
     * class java.nio.HeapByteBuffer  - 使用java 堆内存，读写效率低，会受到GC垃圾回收的影响
     * class java.nio.DirectByteBuffer - 使用直接内存，读写效率高(少一次拷贝)，不会受到GC垃圾回收的影响，分配的效率低
     */
    public static void init(){

        final Class<? extends ByteBuffer> allocateClass = ByteBuffer.allocate(16).getClass();
        final Class<? extends ByteBuffer> allocateDirectClass = ByteBuffer.allocateDirect(16).getClass();
        log.debug("HeapByteBuffer： {}", allocateClass);
        log.debug("DirectByteBuffer： {}", allocateDirectClass);
    }
}
