package com.liuqi.netty.c0;

import io.netty.buffer.ByteBuf;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
 *@ClassName AioFileChannel
 *@Description AioFileChannel
 *@Author LiuQi
 *@Date 2023/2/7 20:00
 *@Version 1.0
 */
@Slf4j
public class AioFileChannel {
    @SneakyThrows
    public static void main(String[] args) {
        String path = "data.txt";
        async(path);
    }

    /**
     * 异步文件获取
     * @param path 文件路径
     * @throws IOException
     */
    private static void async(String path) throws IOException {
        try (final AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(path), StandardOpenOption.READ)){
            // 参数一：ByteBuffer  参数二：读取的起始位置 参数三：附件 参数四：回调对象
            final ByteBuffer buffer = ByteBuffer.allocate(16);
            log.info("READ BEGIN.....");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() { //回调对象
                @Override
                public void completed(Integer result, ByteBuffer attachment) { // 读取成功
                    // 切换读模式
                    attachment.flip();
                    StringBuilder builder = new StringBuilder();
                    while (true){
                       final char c = (char) attachment.get();
                       builder.append(c);
                       log.info("READ INFO： {}",builder.toString());
                    }


                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) { // 读取失败
                    exc.printStackTrace();
                }
            });
            log.info("READ END.......");
        }catch (IOException e){
            e.printStackTrace();
        }
        // 防止主线程 结束
        System.in.read();
    }


}
