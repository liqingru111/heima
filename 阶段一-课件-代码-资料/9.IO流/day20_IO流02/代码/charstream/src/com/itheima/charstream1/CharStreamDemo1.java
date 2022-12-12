package com.itheima.charstream1;

import java.io.FileInputStream;
import java.io.IOException;

public class CharStreamDemo1 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("charstream\\a.txt");
        int b ;
        while((b = fis.read()) != -1){
            System.out.println((char) b);
        }
        fis.close();
    }
}
