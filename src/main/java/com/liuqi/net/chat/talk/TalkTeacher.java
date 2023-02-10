package com.liuqi.net.chat.talk;

import lombok.extern.slf4j.Slf4j;

/*
 *@ClassName TalkTeacher
 *@Description TalkTeacher
 *@Author LiuQi
 *@Date 2023/2/10 14:45
 *@Version 1.0
 */
@Slf4j
public class TalkTeacher {
    public static void main(String[] args) {
        // 开启两个线程
        new Thread(new TalkSend(5555,"127.0.0.1",8888)).start();
        new Thread(new TalkReceive(9999,"学生")).start();
    }
}
