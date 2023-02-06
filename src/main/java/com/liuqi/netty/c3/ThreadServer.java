package com.liuqi.netty.c3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/*
 *@ClassName ThreadServer
 *@Description ThreadServer
 *@Author LiuQi
 *@Date 2023/2/6 18:12
 *@Version 1.0
 */
public class ThreadServer {
    public static void main(String[] args) throws IOException {
        baseServer();
    }

    /**
     * 基本连接服务
     * @throws IOException
     */
    private static void baseServer() throws IOException {
        // 创建server socket 服务
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 开启非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 创建 selector 对象 可以管理多个channel
        final Selector selector = Selector.open();
        // 注册时 关注 accept 事件
        serverSocketChannel.register(selector,  SelectionKey.OP_ACCEPT, null);
        // 绑定 ip 端口号
        serverSocketChannel.bind(new InetSocketAddress(8080));
        while (true){
            // select 方法 没有事件发生 线程阻塞 有事件发生线程恢复运行
            selector.select();
            // 处理事件 selectedKeys可读可写连接事件集合 内部包含了所有发生的事件 Iterator遍历
            final Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                //  通过iterator 获取SelectionKey
                final SelectionKey selectionKey = selectionKeyIterator.next();
                // 删除SelectionKey 必须 从selectionKeys 集合中删除，否则下次处理就有问题报错
                selectionKeyIterator.remove();
                // 区分事件类型
                if(selectionKey.isAcceptable())
                { // 连接事件
                    // socket 服务连接
                    final SocketChannel socketChannel = serverSocketChannel.accept();
                    // 开启非阻塞模式
                    socketChannel.configureBlocking(false);
                }

            }
        }



    }
}
