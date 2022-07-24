package com.wjn.threadcode;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo implements Runnable{

    ReentrantLock lock = new ReentrantLock();
    static int i = 0;

    @Override
    public void run() {
        for(int j = 0; j < 10000; j++) {
            lock.lock();
            try {
                i++;
            }finally {
                lock.unlock();
            }
        }

    }


    public static void main(String[] args) throws InterruptedException {

        ReentrantLockDemo rthread = new ReentrantLockDemo();
        Thread t1 = new Thread(rthread);
        Thread t2 = new Thread(rthread);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);

    }
}
