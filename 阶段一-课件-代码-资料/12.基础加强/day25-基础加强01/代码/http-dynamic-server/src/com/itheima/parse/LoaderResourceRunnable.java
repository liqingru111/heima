package com.itheima.parse;

public class LoaderResourceRunnable implements  Runnable {
    @Override
    public void run() {
        //执行parse方法
        ParseServletConfig parseServletConfig = new PropertiesParseServletConfig();
        parseServletConfig.parse();



    }
}
