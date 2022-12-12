package com.itheima.threadstatedemo;

public class ThreadStateDemo1 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            System.out.println("执行start方法之后,线程的状态" + Thread.currentThread().getState());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("sleep方法结束之后,线程的状态" + Thread.currentThread().getState());
        });

        System.out.println("创建一个线程,但是没有调用start方法" + thread.getState());

        thread.start();

        Thread.sleep(50);

        System.out.println("线程在执行sleep方法时候的状态" + thread.getState());

        Thread.sleep(200);

        System.out.println("线程执行完毕之后的状态" + thread.getState());




    }
}
