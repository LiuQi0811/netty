package com.liuqi.net.chat.talk;

import lombok.extern.slf4j.Slf4j;

/*
 *@ClassName TalkStudent
 *@Description TalkStudent
 *@Author LiuQi
 *@Date 2023/2/10 14:41
 *@Version 1.0
 */
@Slf4j
public class TalkStudent {
    public static void main(String[] args) {
        // 开启两个线程
        new Thread(new TalkSend(3333,"127.0.0.1",9999)).start();
        new Thread(new TalkReceive(8888,"老师")).start();
    }
}
