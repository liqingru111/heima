package com.itheima.myniodemo9;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * 用来封装请求数据的类
 */
public class HttpRequest {
    private String method; //请求方式
    private String requestURI; //请求的uri
    private String version;   //http的协议版本

    private HashMap<String,String> hm = new HashMap<>();//所有的请求头

    //parse --- 获取请求数据 并解析
    public void parse(SelectionKey selectionKey){
        try {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

            StringBuilder sb = new StringBuilder();
            //创建一个缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int len;
            //循环读取
            while((len = socketChannel.read(byteBuffer)) > 0){
                byteBuffer.flip();
                sb.append(new String(byteBuffer.array(),0,len));
                //System.out.println(new String(byteBuffer.array(),0,len));
                byteBuffer.clear();
            }
            //System.out.println(sb);
            parseHttpRequest(sb);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //解析http请求协议中的数据
    private void parseHttpRequest(StringBuilder sb) {
        //1.需要把StringBuilder先变成一个字符串
        String httpRequestStr = sb.toString();
        if(!(httpRequestStr == null || "".equals(httpRequestStr))){
            //2.获取每一行数据
            String[] split = httpRequestStr.split("\r\n");
            //3.获取请求行
            String httpRequestLine = split[0];//GET / HTTP/1.1
            //4.按照空格进行切割,得到请求行中的三部分
            String[] httpRequestInfo = httpRequestLine.split(" ");
            this.method = httpRequestInfo[0];
            this.requestURI = httpRequestInfo[1];
            this.version = httpRequestInfo[2];
            //5.操作每一个请求头
            for (int i = 1; i < split.length; i++) {
                String httpRequestHeaderInfo = split[i];//Host: 127.0.0.1:10000
                String[] httpRequestHeaderInfoArr = httpRequestHeaderInfo.split(": ");
                hm.put(httpRequestHeaderInfoArr[0],httpRequestHeaderInfoArr[1]);
            }
        }
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public HashMap<String, String> getHm() {
        return hm;
    }

    public void setHm(HashMap<String, String> hm) {
        this.hm = hm;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", requestURI='" + requestURI + '\'' +
                ", version='" + version + '\'' +
                ", hm=" + hm +
                '}';
    }
}
