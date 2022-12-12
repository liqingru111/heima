package com.itheima.myniodemo1;

import java.nio.ByteBuffer;

public class ByteBufferDemo3 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put("abc".getBytes());

//        flip()  切换读写模式（写读）
        byteBuffer.flip();
//        get()   读一个字节
//        while(byteBuffer.limit() != byteBuffer.position()){
//            System.out.println((char) byteBuffer.get());
//        }

        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.println((char) byteBuffer.get());
        }


//        get(byte[] dst) 读多个字节
//        byte [] bytes = new byte[byteBuffer.limit()];
//        byteBuffer.get(bytes);
//        System.out.println(new String(bytes));

//        get(int index)  读指定索引的字节
//        System.out.println((char) byteBuffer.get(0));


//        rewind()    将position设置为0，可以重复读
//        byteBuffer.rewind();
//        for (int i = 0; i < byteBuffer.limit(); i++) {
//            System.out.println((char) byteBuffer.get());
//        }


//        clear()     数据读写完毕（读->写）
        byteBuffer.clear();
        byteBuffer.put("qqq".getBytes());
//        array()     将缓冲区转换成字节数组返回

        byte[] bytes = byteBuffer.array();
        System.out.println(new String(bytes));

    }
}
