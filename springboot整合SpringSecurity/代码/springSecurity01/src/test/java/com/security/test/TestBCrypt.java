package com.security.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestBCrypt {

    @Autowired
    PasswordEncoder passwordEncoder;
    /*
    明文，加密成密文
     */
    @Test
    public void  test1(){
        String password="123";
        String encode = passwordEncoder.encode(password);
        System.out.println(encode);
        //$2a$10$j1ZIQnzTX5z/Aw8q6cuRLOGGjn2y/I3EgHLLso4HMcgfYQ7lMOISG
    }
    //判断明文和密文是否一致
    @Test
    public void  test2(){
        String password="123";
        String encode="$2a$10$j1ZIQnzTX5z/Aw8q6cuRLOGGjn2y/I3EgHLLso4HMcgfYQ7lMOISG";
        boolean matches = passwordEncoder.matches(password, encode);
        System.out.println(matches);
    }

}
