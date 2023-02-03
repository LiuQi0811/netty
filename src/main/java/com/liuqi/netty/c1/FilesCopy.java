package com.liuqi.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 *@ClassName FilesCopy
 *@Description FilesCopy 文件拷贝多级目录
 *@Author LiuQi
 *@Date 2023/2/3 15:47
 *@Version 1.0
 */
@Slf4j
public class FilesCopy {
    public static void main(String[] args) throws IOException {
        String source = "/Users/liuqi/Desktop/Apple";
        String target = "/Users/liuqi/Desktop/Apple2";
        walkCopy(source,target);
    }

    /**
     * 文件拷贝传输
     * @param source 原始文件
     * @param target 目标文件
     * @throws IOException
     */
    private static void walkCopy(String source,String target) throws IOException {
           Files.walk(Paths.get(source)).forEach(path->{
              try {
                  final String target_ = path.toString().replace(source, target);
                  if (Files.isDirectory(path)) { // path 是目录
                      // 创建文件目录
                      Files.createDirectories(Paths.get(target_));
                  }else if(Files.isRegularFile(path)) { // path 是文件
                      // 参数一 原始文件  参数二 目标文件  文件拷贝
                      Files.copy(path,Paths.get(target_));
                  }
                  log.info("文件拷贝传输完成");
              }catch (IOException e){
                  e.printStackTrace();
              }
           });
    }
}
