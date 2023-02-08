package com.liuqi.nio.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/*
 *@ClassName GatheringWrite
 *@Description GatheringWrite 集中写
 *@Author LiuQi
 *@Date 2023/2/2 22:25
 *@Version 1.0
 */
@Slf4j
public class GatheringWrite {

    public static void main(String[] args) {
        writer("w.txt","rw");
    }

    /**
     * 写入数据
     * @param file 文件
     * @param mode 模式
     */
    public static void writer(String file,String mode){
        final ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("你是我的眼 ");
        final ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("暖暖 ");
        final ByteBuffer buffer3 = StandardCharsets.UTF_8.encode("狂飙");

        try (final FileChannel channel = new RandomAccessFile(file,mode).getChannel()){
            channel.write(new ByteBuffer[]{buffer1,buffer2,buffer3});
            log.debug("写入文件成功，大小 {} ",channel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
