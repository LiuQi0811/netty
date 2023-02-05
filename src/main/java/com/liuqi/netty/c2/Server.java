package com.liuqi.netty.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
 *@ClassName Server
 *@Description 服务端
 *@Author LiuQi
 *@Date 2023/2/3 16:53
 *@Version 1.0
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        socketChannelSelector();
    }

    /**
     * 服务
     * 使用 nio 理解阻塞模式，单线程
     *
     * @throws IOException
     */
    private static void socketChannelBlocking() throws IOException {
        // 创建缓冲区
        final ByteBuffer byteBuffer = ByteBuffer.allocate(30);
        // 创建 server socket 服务器
        final ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 绑定监听端口
        socketChannel.bind(new InetSocketAddress("localhost", 8080));

        // 创建连接集合
        final List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            log.info("开始创建服务连接中.......");
            // 创建服务连接  accept建立与客户端连接 tcp 三次握手  SocketChannel用来与客户端之间通信
            final SocketChannel socket = socketChannel.accept(); //阻塞方法，线程停止运行 空闲状态
            log.info("创建服务结束....... {}", socket);
            channels.add(socket);
            for (SocketChannel channel : channels) {
                log.info("数据开始读取中...... {}", channel);
                // 接收客户端发送的消息
                final int read = channel.read(byteBuffer);//阻塞方法，线程停止运行 空闲状态
                log.info("read 读取字节数量： {}", read);
                // 切换读模式
                byteBuffer.flip();
                if (byteBuffer.hasRemaining()) { //剩余未读数据
                    log.info("{}", Charset.defaultCharset().decode(byteBuffer));
                }
                // 切换写模式
                byteBuffer.clear();
                log.info("数据读取结束......  {}", channel);
            }


        }
    }


    /**
     * 服务
     * 使用 nio 理解非阻塞模式，单线程
     *
     * @throws IOException
     */
    private static void socketChannelNoBlocking() throws IOException {
        // 创建缓冲区
        final ByteBuffer byteBuffer = ByteBuffer.allocate(30);
        // 创建 server socket 服务器
        final ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 设置非阻塞模式
        socketChannel.configureBlocking(false);
        // 绑定监听端口
        socketChannel.bind(new InetSocketAddress( 8080));

        // 创建连接集合
        final List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            log.info("开始创建服务连接中.......");
            // 创建服务连接  accept建立与客户端连接 tcp 三次握手  SocketChannel用来与客户端之间通信
            final SocketChannel socket = socketChannel.accept(); //非阻塞方法，线程会继续运行,如果没有连接建立socket 为null
            if(socket != null){ // 创建服务连接不为null
                log.info("创建服务结束....... {}", socket);
                // 设置非阻塞模式
                socket.configureBlocking(false);
                channels.add(socket);
            }
            for (SocketChannel channel : channels) {
                log.info("数据开始读取中...... {}", channel);
                // 接收客户端发送的消息
                final int read = channel.read(byteBuffer);//非阻塞方法，线程会继续运行，如果没有读到数据read返回0
                log.info("read 读取字节数量： {}", read);
                if(read > 0){ //read 大于 0 读取数据
                    // 切换读模式
                    byteBuffer.flip();
                    if (byteBuffer.hasRemaining()) { //剩余未读数据
                        log.info("{}", Charset.defaultCharset().decode(byteBuffer));
                    }
                    // 切换写模式
                    byteBuffer.clear();
                    log.info("数据读取结束......  {}", channel);
                }
            }
        }
    }

    /**
     *  socketChannelSelector
     * @throws IOException
     */
    private static void socketChannelSelector() throws IOException {
        // 创建 selector 对象 可以管理多个channel
        final Selector selector = Selector.open();
        // 创建缓冲区
        final ByteBuffer byteBuffer = ByteBuffer.allocate(30);
        // 创建server socket 服务
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 创建 selector 和 channel的联系 （注册）
        // SelectionKey 将来事件发生后 通过他可以知道事件和哪个channel的事件   0代表不关注任何事件
        final SelectionKey serverSocketKey = serverSocketChannel.register(selector, 0, null);
        log.info("serverSocketKey: {}",serverSocketKey);
        // SelectionKey 只关注 ACCEPT事件
        serverSocketKey.interestOps(SelectionKey.OP_ACCEPT);
        // 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8080));
        while (true){
            // select 方法 没有事件发生 线程阻塞 有事件发生线程恢复运行
            selector.select();
            // 处理事件 selectedKeys可读可写连接事件集合 内部包含了所有发生的事件
            final Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历数据
            final Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                //  通过iterator 获取SelectionKey
                final SelectionKey selectionKey = keyIterator.next();
                // 删除SelectionKey 必须 从selectionKeys 集合中删除，否则下次处理就有问题报错
                keyIterator.remove();
                log.info("selectionKey: {}",selectionKey);
                // 区分事件类型
                if (selectionKey.isAcceptable()){ // accept事件  ServerSocketChannel触发的
                    // 创建server socket服务
                    final ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();// 拿到触发事件的channel
                    // 创建服务连接  accept建立与客户端连接 tcp 三次握手  SocketChannel用来与客户端之间通信
                    final SocketChannel socketChannel = channel.accept();
                    log.info("{}",socketChannel);
                    // 非阻塞模式
                    socketChannel.configureBlocking(false);
                    final SelectionKey socketKey = socketChannel.register(selector, 0, null);
                    socketKey.interestOps(SelectionKey.OP_READ);
                    log.info("socketKey {}",socketKey);
                }else if (selectionKey.isReadable()){ // read 可读事件 SocketChannel 读取
                    try {
                        final SocketChannel channel = (SocketChannel)selectionKey.channel(); // 拿到触发事件的channel
                        // 创建缓冲区
                        final ByteBuffer buffer = ByteBuffer.allocate(30);
                        // 读取数据 实际字节数 如果正常断开连接返回值 -1
                        final int len = channel.read(buffer);
                        if(len == -1){
                            // 取消(从selector的key集合中真正删除)selectionKey
                            selectionKey.cancel();
                        }else {
                            // 切换读取模式
                            buffer.flip();
                            // 读取中文字符
                            log.info("INFO READ:  {}",Charset.defaultCharset().decode(buffer));
                        }

                    }catch (IOException e){
                        e.printStackTrace();
                        // 因为客户端断开了，取消(从selector的key集合中真正删除)selectionKey
                        selectionKey.cancel();
                    }
                }
            }

        }
    }
}
