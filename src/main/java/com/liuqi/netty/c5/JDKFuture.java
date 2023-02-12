package com.liuqi.netty.c5;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 *@ClassName JDKFuture
 *@Description JDKFuture
 *@Author LiuQi
 *@Date 2023/2/12 10:05
 *@Version 1.0
 */
@Slf4j
public class JDKFuture {
    @SneakyThrows
    public static void main(String[] args) {
        // 创建线程池
        final ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 提交任务
        final Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("执行计算");
                Thread.sleep(1000);
                return 100;
            }
        });

        // 主线程通过future来获取结果
        log.info("执行结果");
        log.info("返回结果是：{}",future.get());
    }
}
