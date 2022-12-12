package com.itheima.convertedio;

import java.io.*;
import java.util.ArrayList;

public class ConvertedDemo5 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //1.写对象
        //2.修改javabean类
        //3.读对象

        //写对象 --- 序列化
        //method1();

        //读对象 --- 反序列化
       method2();

    }

    private static void method2() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("a.txt"));
        User o = (User) ois.readObject();
        System.out.println(o);
        ois.close();
    }

    private static void method1() throws IOException {
        User user = new User("zhangsan","qwer");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("a.txt"));
        oos.writeObject(user);
        oos.close();
    }
}
