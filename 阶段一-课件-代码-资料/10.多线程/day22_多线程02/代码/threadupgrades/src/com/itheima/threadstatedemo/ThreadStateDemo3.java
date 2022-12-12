package com.itheima.threadstatedemo;

//NEW---RUNNABLE---BLOCKED---RUNNABLE---TERMINATED
public class ThreadStateDemo3 {
    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();

        Thread t1 = new Thread(()->{
            //抢锁的线程
            synchronized (obj){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();

        Thread t2 = new Thread(()->{
            System.out.println("线程开启之后的状态" + Thread.currentThread().getState());

            synchronized (obj){
                System.out.println("进入之后的线程状态" + Thread.currentThread().getState());
            }
        });

        System.out.println("创建线程对象后,但是不调用start方法的状态" + t2.getState());
        t2.start();

        Thread.sleep(100);
        System.out.println("线程无法获取锁对象时的状态" + t2.getState());

        Thread.sleep(2000);
        System.out.println("线程完全执行完毕时的状态" + t2.getState());



    }
}
