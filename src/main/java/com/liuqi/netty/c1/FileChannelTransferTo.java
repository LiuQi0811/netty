package com.liuqi.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;

/*
 *@ClassName FileChannelTransferTo
 *@Description FileChannelTransferTo 文件传输  数据拷贝
 *@Author LiuQi
 *@Date 2023/2/3 14:10
 *@Version 1.0
 */
@Slf4j
public class FileChannelTransferTo {
    public static void main(String[] args) {
        transferTo();
    }


    public static void transferTo(){
        final long start = System.currentTimeMillis();
        try (
                // 原始数据
                final FileChannel from = new FileInputStream("from.txt").getChannel();
                // 目标数据
                final FileChannel to = new FileOutputStream("to.txt").getChannel()
        ){

            // transferTo  数据传输 效率高 底层会利用零拷贝进行优化
            // 参数一 起始位置 参数二 数据大小 参数三 传输位置
            from.transferTo(0,from.size(),to);
            final long end = System.currentTimeMillis();
            log.debug("数据传输完成: {}", (end-start)+"ms");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
