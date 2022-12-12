package com.itheima.myniodemo2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws IOException {
        //1.打开通道
        SocketChannel socketChannel = SocketChannel.open();

        //2.指定IP和端口号
        socketChannel.connect(new InetSocketAddress("127.0.0.1",10000));

        //3.写出数据
        ByteBuffer byteBuffer = ByteBuffer.wrap("一点寒毛先制".getBytes());
        socketChannel.write(byteBuffer);

        //4.释放资源
        socketChannel.close();
    }
}
