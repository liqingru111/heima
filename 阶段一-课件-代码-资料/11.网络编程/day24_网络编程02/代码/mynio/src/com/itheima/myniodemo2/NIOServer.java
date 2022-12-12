package com.itheima.myniodemo2;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

public class NIOServer {
    public static void main(String[] args) throws IOException {
//        1，打开一个服务端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//        2，绑定对应的端口号
        serverSocketChannel.bind(new InetSocketAddress(10000));
//        3，通道默认是阻塞的，需要设置为非阻塞
            //如果传递true 表示通道设置为阻塞通道...默认值
            //如果传递false 表示通道设置为非阻塞通道
        serverSocketChannel.configureBlocking(false);
//        4，此时没有门卫大爷，所以需要经常看一下有没有连接发过来没？
        while (true) {
//        5，如果有客户端来连接了，则在服务端通道内部，再创建一个客户端通道，相当于是客户端通道的延伸
            //此时已经设置了通道为非阻塞
            //所以在调用方法的时候,如果有客户端来连接,那么会创建一个SocketChannel对象.
            //如果在调用方法的时候,没有客户端来连接,那么他会返回一个null
            SocketChannel socketChannel = serverSocketChannel.accept();
            //System.out.println(socketChannel);
            if(socketChannel != null){
//        6，客户端将缓冲区通过通道传递给服务端,就到了这个延伸通道socketChannel里面
//        7，服务端创建一个空的缓冲区装数据并输出
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                //获取传递过来的数据,并把他们放到byteBuffer缓冲区中.
                //返回值:
                    //正数: 表示本次读到的有效字节个数.
                    //0   : 表示本次没有读到有效字节.
                    //-1  : 表示读到了末尾
                int len = socketChannel.read(byteBuffer);
                System.out.println(new String(byteBuffer.array(),0,len));
                socketChannel.close();
            }
        }



    }
}
