package com.itheima.myniodemo6;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {
    private String version; //协议版本
    private String status;  //响应状态码
    private String desc;    //状态码的描述信息

    //响应头数据
    private HashMap<String, String> hm = new HashMap<>();

    private HttpRequest httpRequest;  //我们后面要根据请求的数据,来进行一些判断

    //给浏览器响应数据的方法
    public void sendStaticResource(SelectionKey selectionKey) {
        //1.给响应行赋值
        this.version = "HTTP/1.1";
        this.status = "200";
        this.desc = "ok";
        //2.将响应行拼接成一个单独的字符串 // HTTP/1.1 200 ok
        String responseLine = this.version + " " + this.status + " " + this.desc + "\r\n";

        //3.给响应头赋值
        hm.put("Content-Type", "text/html;charset=UTF-8");

        //4.将所有的响应头拼接成一个单独的字符串
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> entries = hm.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }

        //5.响应空行
        String emptyLine = "\r\n";

        //6.响应行,响应头,响应空行拼接成一个大字符串
        String responseLineStr = responseLine + sb.toString() + emptyLine;

        try {
            //7.将上面三个写给浏览器
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer1 = ByteBuffer.wrap(responseLineStr.getBytes());
            socketChannel.write(byteBuffer1);

            //8.单独操作响应体
            //因为在以后响应体不一定是一个字符串
            //有可能是一个文件,所以单独操作
            String s = "哎哟,妈呀,终于写完了.";
            ByteBuffer byteBuffer2 = ByteBuffer.wrap(s.getBytes());
            socketChannel.write(byteBuffer2);

            //9.释放资源
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public HashMap<String, String> getHm() {
        return hm;
    }

    public void setHm(HashMap<String, String> hm) {
        this.hm = hm;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "version='" + version + '\'' +
                ", status='" + status + '\'' +
                ", desc='" + desc + '\'' +
                ", hm=" + hm +
                ", httpRequest=" + httpRequest +
                '}';
    }
}
