package com.wjn.threadcode;

/**
 * @Author: wjn
 * @Data: 2023/4/12 9:48
 */

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock读写锁，用锁分离的机制来提升性能，比如线程A1,A2,A3进行写操作，B1,B2,B3进行读操作，如果使用重入锁或者内部所，理论上说
 * 所有读之间、读与写之间、写与写之间都是串行操作。当B1进行读取时，B2,B3则需要等待锁，由于读操作并不对数据的完整性造成破坏，这种等待显然
 * 是不合理的，因此，读写锁就有了发挥功能的余地
 */
public class ReadWriteLockDemo {
    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();
    private int value;

    public Object handleRead(Lock lock) throws InterruptedException {
        try{
            lock.lock();
            Thread.sleep(1000);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void handleWrite(Lock lock, int index) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            value = index;
        } finally {
            lock.unlock();
        }
    }

    static class readRunnable implements Runnable {
        private CountDownLatch latch;
        private ReadWriteLockDemo demo;

        public readRunnable(ReadWriteLockDemo demo,CountDownLatch latch) {
            this.latch = latch;
            this.demo = demo;
        }

        @Override
        public void run() {
            try {
                demo.handleRead(readLock);
                // 不用读写锁，需要20s才能结束
//                demo.handleRead(lock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }


    static class writeRunnable implements Runnable {
        private CountDownLatch latch;
        private ReadWriteLockDemo demo;

        public writeRunnable(ReadWriteLockDemo demo,CountDownLatch latch) {
            this.latch = latch;
            this.demo = demo;
        }

        @Override
        public void run() {
            try {
                // 用读写锁，2s可结束
                demo.handleWrite(writeLock, new Random().nextInt());
                // 不用读写锁，需要20s才能结束
//                demo.handleWrite(lock, new Random().nextInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        CountDownLatch countDownLatch = new CountDownLatch(20);

        long beginTime = System.currentTimeMillis();

        for (int i = 0; i < 19; i++) {
            new Thread(new readRunnable(readWriteLockDemo, countDownLatch)).start();
        }

        for (int i = 18; i < 20; i++) {
            new Thread(new writeRunnable(readWriteLockDemo, countDownLatch)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("耗时:" + (endTime - beginTime));

    }


}
