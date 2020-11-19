package com.chen.netty.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServerDemo {

    private int port = 8080;

    //准备两个东西
    //轮询器 Selector 大堂经理
    private Selector selector;
    //缓冲区 Buffer 等候区
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    //初始化
    public NIOServerDemo(int port){
        try{
            this.port = port;
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(this.port));
            server.configureBlocking(false);

            selector = Selector.open();

            server.register(selector, SelectionKey.OP_ACCEPT);

        }catch (Exception ex){

        }
    }

    public void listen(){
        System.out.println("listen on "+ this.port + ".");
        try {
            while (true){
                selector.select();
                Set<SelectionKey> keys =  selector.selectedKeys();
                Iterator<SelectionKey> inter = keys.iterator();
                while (inter.hasNext()){
                    SelectionKey key = inter.next();
                    inter.remove();
                    process(key);
                }
            }
        }catch (Exception ex){

        }
    }

    //具体办业务的方法，坐班柜员
    //每次轮询就是调用一次process方法，而每一次调用，就只能干一件事情
    //在同一时间点，只能干一件事
    private void process(SelectionKey key) {
        try {
            //针对于每一种状态给一个反应
            if(key.isAcceptable()){
                ServerSocketChannel server = (ServerSocketChannel) key.channel();
                //这个方法体现非阻塞，不管你数据有没有准备好
                //你给我一个状态和反馈
                SocketChannel channel = server.accept();
                channel.configureBlocking(false);
                key = channel.register(selector,SelectionKey.OP_READ);
            }else if(key.isReadable()){
                SocketChannel channel = (SocketChannel) key.channel();
                int len = channel.read(buffer);
                if(len > 0){
                    buffer.flip();
                    String content = new String(buffer.array(),0,len);
                    key = channel.register(selector,SelectionKey.OP_WRITE);
                    key.attach(content);
                    System.out.println("读取内容：" + content);
                }
            }else if(key.isWritable()){
                SocketChannel channel = (SocketChannel)key.channel();
                String content = (String)key.attachment();
                channel.write(ByteBuffer.wrap(("输出: " + content).getBytes()));

                channel.close();

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new NIOServerDemo(8080).listen();
    }
}
