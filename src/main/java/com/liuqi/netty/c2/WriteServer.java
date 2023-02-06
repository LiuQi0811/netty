package com.liuqi.netty.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/*
 *@ClassName WriteServer
 *@Description WriteServer 服务端写入数据
 *@Author LiuQi
 *@Date 2023/2/6 09:25
 *@Version 1.0
 */
@Slf4j
public class WriteServer {
    public static void main(String[] args) throws IOException {
        // 创建server socket 服务
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 开启非阻塞方法
        serverSocketChannel.configureBlocking(false);
        // 创建 selector 对象 可以管理多个channel
        final Selector selector =Selector.open();
        // 注册时 关注 accept 事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, null);
        // 绑定 ip 端口号
        serverSocketChannel.bind(new InetSocketAddress(8080));

        while (true){
            // select 方法 没有事件发生 线程阻塞 有事件发生线程恢复运行
            selector.select();
            // 处理事件 selectedKeys可读可写连接事件集合 内部包含了所有发生的事件 Iterator遍历
            final Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()){
                //  通过iterator 获取SelectionKey
                final SelectionKey selectionKey = selectionKeyIterator.next();
                // 删除SelectionKey 必须 从selectionKeys 集合中删除，否则下次处理就有问题报错
                selectionKeyIterator.remove();
                // 区分事件类型
                if (selectionKey.isAcceptable()) { // 连接事件
                    final SocketChannel socketChannel = serverSocketChannel.accept();
                    // 开启非阻塞方法
                    socketChannel.configureBlocking(false);
                    // SelectionKey 将来事件发生后 通过他可以知道事件和哪个channel的事件   0代表不关注任何事件
                    final SelectionKey socketKey = socketChannel.register(selector, 0, null);
                    // 注册时 关注 read 事件
                    socketKey.interestOps(SelectionKey.OP_READ);
                    // 向客户端发送大量数据
                    final StringBuilder builder = new StringBuilder();
                    builder
                    .append("A,")
                    .append("B,")
                    .append("C,")
                    .append("D,")
                    .append("E,")
                    .append("F,")
                    .append("G,");
                    final ByteBuffer buffer = Charset.defaultCharset().encode(builder.toString());
                    final int write = socketChannel.write(buffer);
                    log.info("写入数据操作 {} ",write);
                    if(buffer.hasRemaining()){ // 判断是否有剩余内容
                        // 注册时 关注 read write 事件
                        selectionKey.interestOps(selectionKey.interestOps() + SelectionKey.OP_WRITE);
                        // 数据扩容 把未写完的数据挂载 SelectionKey上
                        selectionKey.attach(buffer);
                    }

                }else if (selectionKey.isWritable()){ // 写入事件
                    //获取 SelectionKey 上关联的附件数据
                    final ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    final SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    final int write = socketChannel.write(buffer);
                    log.info("写入数据长度： {}",write);
                    // 清理
                    if (!buffer.hasRemaining()) {
                        // 数据挂载 SelectionKey上 设置null  清除buffer
                        selectionKey.attach(null);
                        // 不需要关注可写事件
                        selectionKey.interestOps(selectionKey.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
                log.info("{}",selectionKey);
            }


        }

    }
}
