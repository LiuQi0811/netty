package com.liuqi.net.c1;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/*
 *@ClassName InetAddressInfo
 *@Description InetAddressInfo
 *@Author LiuQi
 *@Date 2023/2/9 12:54
 *@Version 1.0
 */
@Slf4j
public class InetAddressInfo
{
    public static void main(String[] args) {
        getAddress();
    }

    /**
     * 获取IP信息
     */
    @SneakyThrows
    private static void getAddress(){
        // 获取本机ip地址
        final InetAddress localhost = InetAddress.getByName("localhost");
        final InetAddress localHost = InetAddress.getLocalHost();
        log.info("    本机ip地址: {}" ,localhost);
        log.info("    本机ip地址: {}" ,localHost);
        log.info("    本机ip地址: {}" ,InetAddress.getLoopbackAddress());
        // 获取其他ip地址
        final InetAddress other = InetAddress.getByName("www.baidu.com");
        log.info("    其他ip地址: {}" ,other);

    }
}
