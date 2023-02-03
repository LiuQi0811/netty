package com.liuqi.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/*
 *@ClassName FilesWalkFileTree
 *@Description FilesWalkFileTree
 *@Author LiuQi
 *@Date 2023/2/3 15:08
 *@Version 1.0
 */
@Slf4j
public class FilesWalkFileTree {
    public static void main(String[] args) throws IOException {
            tree("/Users/liuqi/Desktop/Apple");
//            delTree("/Users/liuqi/Desktop/Apple2");
    }

    /**
     * 遍历文件树 统计文件夹，文件数量
     * @param path
     * @throws IOException
     */
    private static void tree(String path) throws IOException {
        // 文件夹计数器
        final AtomicInteger dirCount = new AtomicInteger();
        // 文件计数器
        final AtomicInteger fileCount = new AtomicInteger();
        // 这里不能用 int count = 0; count++;
        Files.walkFileTree(Paths.get(path),new SimpleFileVisitor<Path>(){
            // 进入
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                log.debug("Directory  {}",dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                log.debug("File  {}",file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }

            // 退出
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return super.postVisitDirectory(dir, exc);
            }
        });


        log.debug("文件夹统计数据：{}",dirCount);
        log.debug("文件统计数据：{}",fileCount);
    }


    /**
     *  删除 文件树
     * @param path
     */
    private static void delTree(String path) throws IOException {
        Files.walkFileTree(Paths.get(path),new SimpleFileVisitor<Path>(){
            // 删除文件
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                log.debug("文件 {} 已删除",file);
                return super.visitFile(file, attrs);
            }
            //退出
            // 删除文件夹
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                log.debug("文件目录 {} 已删除",dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }
}
