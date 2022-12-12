package com.itheima.threadstatedemo;

//NEW--->RUNNABLE--->WAITING--->RUNNABLE--->TERMINATED
public class ThreadStateDemo2 {
    public static void main(String[] args) throws InterruptedException {
        //锁对象
        Object obj = new Object();

        Thread thread1 = new Thread(()->{
            System.out.println("线程开启之后的状态" + Thread.currentThread().getState());

            synchronized (obj){


                try {
                    Thread.sleep(100);
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("线程被唤醒之后的状态" + Thread.currentThread().getState());
            }
        });

        System.out.println("创建线程对象后,但是不调用start方法的状态" + thread1.getState());

        thread1.start();

        Thread.sleep(200);

        System.out.println("线程thread1进入到wait方法时的状态" + thread1.getState());

        new Thread(()->{
            synchronized (obj){
                obj.notifyAll();
            }
        }).start();

        //为了让thread1线程先把代码执行完毕
        Thread.sleep(200);

        System.out.println("线程执行完毕之后的状态" + thread1.getState());



    }
}
