package com.liuqi.netty.c2;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/*
 *@ClassName EventLoop
 *@Description EventLoop
 *@Author LiuQi
 *@Date 2023/2/8 19:10
 *@Version 1.0
 */
@Slf4j
public abstract class EventLoop {
    public static void main(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = null;
        commitTask(nioEventLoopGroup);
        timerTask(nioEventLoopGroup, 6);
    }

    private static void init() {
        // 创建事件循环组
        final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(); // IO事件，普通任务，定时任务
        // 获取下一个事件循环对象
        nioEventLoopGroup.next();
        final DefaultEventLoopGroup defaultEventLoopGroup = new DefaultEventLoopGroup(); // 普通任务，定时任务
        // 获取下一个事件循环对象
        defaultEventLoopGroup.next();
    }

    /**
     * 普通任务
     */

    private static void commitTask(NioEventLoopGroup nioEventLoopGroup) {
        // 创建事件循环组
        nioEventLoopGroup = new NioEventLoopGroup(2); // IO事件，普通任务，定时任务
        log.info("{}", nioEventLoopGroup.next());
        log.info("{}", nioEventLoopGroup.next());
        log.info("{}", nioEventLoopGroup.next());
        log.info("{}", nioEventLoopGroup.next());
        nioEventLoopGroup.next().submit(() -> { // 执行普通任务
            try {
                // 线程睡眠 1s
                Thread.sleep(1000);
                log.info("Ok");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 主线程打印
        log.info("Main");
    }

    /**
     * 定时任务
     * @param nioEventLoopGroup  事件循环组
     * @param time 时间
     */
    private static void timerTask(NioEventLoopGroup nioEventLoopGroup, long time) {
        // 创建事件循环组
        nioEventLoopGroup = new NioEventLoopGroup(2); // IO事件，普通任务，定时任务
        nioEventLoopGroup.next()
                // 参数一：Runnable方法  参数二：执行延时  参数三：具体的时间  参数四：TimeUnit类型
                .scheduleAtFixedRate(() -> {
                    // 编写代码逻辑.....
                    log.info("正在执行任务.......");
                }, time, time, TimeUnit.SECONDS);
    }


}
