package com.chen.netty.io.nio;

import java.nio.ByteBuffer;
import java.nio.channels.Selector;

public class NIOServerDemo {
    private int port = 8080;

    //准备两个东西
    //轮询器 Selector 大堂经理
    private Selector selector;
    //缓冲区 Buffer 等候区
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
}
