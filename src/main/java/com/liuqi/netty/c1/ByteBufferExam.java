package com.liuqi.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/*
 *@ClassName ByteBufferExam
 *@Description ByteBufferExam 黏包 半包
 *@Author LiuQi
 *@Date 2023/2/3 11:51
 *@Version 1.0
 */
@Slf4j
public class ByteBufferExam {
    public static void main(String[] args) {
        exam();
    }

    public static void exam() {
        // 创建缓冲区
        final ByteBuffer buffer = ByteBuffer.allocate(100);
        // 缓冲区新增数据
        buffer.put("Hello,World\n, nice to ".getBytes(StandardCharsets.UTF_8));
        split(buffer);
        buffer.put("meet you\n I'm Liu Qi\n".getBytes(StandardCharsets.UTF_8));
        split(buffer);
    }

    private static void split(ByteBuffer buffer) {
        // 切换读模式
        buffer.flip();
        // 获取读上限
        final int limit = buffer.limit();
        log.debug("获取读上限 {}", limit);
        for (int i = 0; i < limit; i++) {
            if (buffer.get(i) == '\n') {
                // 获取数据长度
                int length = i + 1 - buffer.position();
                log.debug("获取数据长度 {}",length);
                // 这条完整消息存入新的ByteBuffer
                final ByteBuffer targetByteBuffer = ByteBuffer.allocate(length);
                for (int i1 = 0; i1 < length; i1++) {
                    targetByteBuffer.put(buffer.get());
                }
                log.debug("{}",targetByteBuffer);
            }
    }
        // 未读的数据向前移动 和未读的部分拼接一起
        buffer.compact();
    }
}
