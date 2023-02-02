package com.liuqi.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/*
 *@ClassName ScatteringReader
 *@Description ScatteringReader 分散读取
 *@Author LiuQi
 *@Date 2023/2/2 21:52
 *@Version 1.0
 */
@Slf4j
public class ScatteringReader {
    public static void main(String[] args) {
        init("word.txt","r");
    }

    /**
     *
     * @param file 读取的文件
     * @param mode 读写模型
     */
    public static void init(String file,String mode){
        try(final FileChannel fileChannel = new RandomAccessFile(file, mode).getChannel()) {
            final ByteBuffer buffer1 = ByteBuffer.allocate(1);
            final ByteBuffer buffer2 = ByteBuffer.allocate(4);
            final ByteBuffer buffer3 = ByteBuffer.allocate(7);
            // 读取 ByteBuffer数组内容
            fileChannel.read( new ByteBuffer[]{buffer1,buffer2,buffer3});
            // 切换读模式
            buffer1.flip();
            buffer2.flip();
            buffer3.flip();
            // 获取读取的内容
            log.debug("{}", StandardCharsets.UTF_8.decode(buffer1));
            log.debug("{}", StandardCharsets.UTF_8.decode(buffer2));
            log.debug("{}", StandardCharsets.UTF_8.decode(buffer3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
