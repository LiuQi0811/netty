package com.liuqi.netty.c5;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.INTERNAL;

import java.util.concurrent.Callable;

/*
 *@ClassName NettyFuture
 *@Description NettyFuture
 *@Author LiuQi
 *@Date 2023/2/12 10:17
 *@Version 1.0
 */
@Slf4j
public class NettyFuture {
    @SneakyThrows
    public static void main(String[] args) {
        // NioEventLoopGroup 线程池组
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        // 获取EventLoop线程
        EventLoop eventLoop = nioEventLoopGroup.next();
        // 提交任务
        final Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("执行计算");
                Thread.sleep(1000);
                return 66;
            }
        });

        // 主线程通过future来获取结果
        log.info("执行结果");
        log.info("返回结果是：{}",future.get());

        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.info("接收结果：{}",future.getNow());
            }
        });

    }
}
