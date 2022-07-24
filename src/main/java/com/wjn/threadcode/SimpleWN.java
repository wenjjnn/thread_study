package com.wjn.threadcode;

/**
 * p43
 * wait() 和 notify() 的案例
 * 无论是wait()或者是notify()都需要首先获得目标对象的一个监视器。它们俩个不是可以随便调用的，必须包含在对应的synchronized语句中。
 * 线程T1线申请object的对象锁，在执行object.wait()时，它是持有object的锁的，wait()方法执行后，T1会进行等待，并释放object的锁
 * T2在执行notify之前也会先获得obejct的对象锁。
 *
 */

public class SimpleWN {

    static final Object object = new Object();

    static class T1 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T1 start! ");
                try {
                    System.out.println(System.currentTimeMillis() + ":T1 wait for object");
                    object.wait(); //释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":T1 end!");
            }
        }
    }

    static class T2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T2 start! notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis() + ":T2 end!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e){
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();

    }
}
