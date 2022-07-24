package com.wjn.threadcode;

import java.util.concurrent.locks.ReentrantLock;

public class FairLock implements Runnable{
    /**
     * p78
     * true表示锁是公平的，当设置为true时，可以看到打印结果是t1 t2交替获得锁的
     * false表示是非公平的，当设置为false时，可以看到打印结果并非交替获得锁的
     */
    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + " 获得锁");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock r1 = new FairLock();
        Thread t1 = new Thread(r1,"Thread_t1");
        Thread t2 = new Thread(r1,"Thread_t2");
        t1.start();t2.start();

    }
}
