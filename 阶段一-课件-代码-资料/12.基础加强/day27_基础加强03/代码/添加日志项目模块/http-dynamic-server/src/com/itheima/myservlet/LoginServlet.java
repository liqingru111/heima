package com.itheima.myservlet;

import com.itheima.httpserver.HttpRequest;
import com.itheima.httpserver.HttpResponse;
import com.itheima.parse.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理登录请求
 */
@WebServlet(urlPatterns = "/servlet/loginservlet")
public class LoginServlet implements HttpServlet{

    //获取日志的对象
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
       //处理
        System.out.println("LoginServlet处理了登录请求");

        LOGGER.info("现在已经处理了登录请求，准备给浏览器响应");

       //响应
        httpResponse.setContentTpye("text/html;charset=UTF-8");
        httpResponse.write("登录成功");
    }
}
