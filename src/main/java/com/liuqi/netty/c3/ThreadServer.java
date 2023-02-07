package com.liuqi.netty.c3;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/*
 *@ClassName ThreadServer
 *@Description ThreadServer
 *@Author LiuQi
 *@Date 2023/2/6 18:12
 *@Version 1.0
 */
@Slf4j
public class ThreadServer {
    public static void main(String[] args) throws IOException {
        baseServer();
    }

    /**
     * 基本连接服务
     * @throws IOException
     */
    private static void baseServer() throws IOException {
        // 创建线程
        Thread.currentThread().setName("Boss");
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
        log.info("服务端正在连接.....");
        final Worker w1 = new Worker("W1");
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
                    log.info("服务连接地址......{}",socketChannel.getRemoteAddress());
                    log.info("注册前......{}",socketChannel.getRemoteAddress());
                    // 初始化线程 和 selector
                    w1.register();
                    // 关联selector
                    socketChannel.register(w1.selector,SelectionKey.OP_READ,null);
                    log.info("注册后 ......{}",socketChannel.getRemoteAddress());
                }

            }
        }
    }

    /**
     * 创建 Worker 内部类
     */
    static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile Boolean start = false; //启动状态 未启动
        // 构造方法
        public  Worker(String name){
            this.name = name;
        }

        /**
         * 初始化线程 和 selector
         */
        public void register() throws IOException {
            if(!start){
                // 初始化线程对象
                this.thread = new Thread(this,name);
                // 启动线程
                this.thread.start();
                // 初始化 selector 对象
                this.selector = Selector.open();
                start = true; //切换启动状态 已启动
            }
        }
        @SneakyThrows
        @Override
        public void run() {
            while (true){
                // select 方法 没有事件发生 线程阻塞 有事件发生线程恢复运行
                this.selector.select();
                // 处理事件 selectedKeys可读可写连接事件集合 内部包含了所有发生的事件 Iterator遍历
                final Iterator<SelectionKey> selectionKeyIterator = this.selector.selectedKeys().iterator();
                while (selectionKeyIterator.hasNext()){
                    // 通过iterator 获取SelectionKey
                    final SelectionKey selectionKey = selectionKeyIterator.next();
                    // 删除SelectionKey 必须 从selectionKeys 集合中删除，否则下次处理就有问题报错
                    selectionKeyIterator.remove();
                    log.info("SelectionKey.isAcceptable::::{}",selectionKey.isAcceptable());
                    log.info("SelectionKey.isReadable::::{}",selectionKey.isReadable());
                    if (selectionKey.isReadable()) { //可读事件
                        //
                        ByteBuffer buffer =  ByteBuffer.allocate(1024);
                        // 通过 SelectionKey 创建 socket 服务连接
                        final SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        log.info("SocketChannel::::{}",selectionKey.attachment());
                        // 创建缓冲区 获取 SelectionKey 上关联的附件数据
                        log.info("{}{}",buffer);
                        // 读取数据
                        final int read = socketChannel.read(buffer);
                        log.info("读取数据长度：{}",read);
                        if (read==-1){
                            // 取消(从selector的key集合中真正删除)selectionKey
                            selectionKey.cancel();
                        }else {
//                            expansion(selectionKey);
                            log.info("POSITION {}",buffer.position());
                            log.info("LIMIT  {}",buffer.limit());
                            buffer.flip();
                            log.info("接收到的数据：{} ", Charset.defaultCharset().decode(buffer));
                            buffer.clear();
                        }

                    }
                }

            }
        }

        /**
         * buffer 扩容
         * @param selectionKey
         */
        private static void expansion(SelectionKey selectionKey){
            ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
            log.info("buffer.capacity()   {}",buffer.capacity());
            //开启读取模式
            buffer.flip();
            if (buffer.hasRemaining()) { //剩余的数据
                log.info("接收到的数据：{} ", Charset.defaultCharset().decode(buffer));
            }
            // 说明buffer 已满 需要扩容
            if(buffer.position() == buffer.limit()){
                ByteBuffer byteBuffer = ByteBuffer.allocate(buffer.capacity()*2);
                // 旧的buffer开启读取模式
                buffer.flip();
                // 数据拷贝传输 newBuffer 新的buffer buffer 旧的buffer数据
                byteBuffer.put(buffer);
                // 数据扩容
                selectionKey.attach(byteBuffer);
            }
        }
    }
}
