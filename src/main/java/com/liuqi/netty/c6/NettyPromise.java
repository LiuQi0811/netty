package com.liuqi.netty.c6;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/*
 *@ClassName NettyPromise
 *@Description NettyPromise
 *@Author LiuQi
 *@Date 2023/2/12 10:37
 *@Version 1.0
 */
@Slf4j
public class NettyPromise {
    @SneakyThrows
    public static void main(String[] args) {
        // 创建 EventLoop对象
        EventLoop eventLoop = new NioEventLoopGroup().next();
        // 创建DefaultPromise对象，结果容器
        DefaultPromise<Integer> defaultPromise = new DefaultPromise<>(eventLoop);
        // 创建线程
        new Thread(()->{
            // 任意一个线程执行计算，计算完毕后向 promise填充结果
            log.info("开始计算......");
            // 错误的运算
            int i = 1/0;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // 获取错误的结果
                defaultPromise.setFailure(e);
            }
            // 设置 返回结果
            defaultPromise.setSuccess(102);
        }).start();

        // 接收结果的线程
        log.info("等待结果......");
        log.info("接收结果是：{}",defaultPromise.get());
    }
}
