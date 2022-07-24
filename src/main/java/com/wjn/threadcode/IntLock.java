package com.wjn.threadcode;

import java.util.concurrent.locks.ReentrantLock;

/**
 * p73
 * 下面的代码产生了一个死锁，但得益于锁中断，我们可以很轻易地解决这个死锁。
 *
 */
public class IntLock implements Runnable {

    int locknum;

    public static ReentrantLock rLock1 = new ReentrantLock();

    public static ReentrantLock rLock2 = new ReentrantLock();

    public IntLock(int locknum) {
        this.locknum = locknum;
    }

    @Override
    public void run() {
        try {
            if (locknum == 1) {
                // lockInterruptibly()可以对中断进行响应的锁申请动作
                rLock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                rLock2.lockInterruptibly();
            } else {
                rLock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    rLock1.lockInterruptibly();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (rLock1.isHeldByCurrentThread()) {
                rLock1.unlock();
            }
            if (rLock2.isHeldByCurrentThread()) {
                rLock2.unlock();
            }
            System.out.println(Thread.currentThread().getId() + ":线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntLock r1 = new IntLock(1);
        IntLock r2 = new IntLock(2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();t2.start();
        Thread.sleep(1000);
        // 中断t2线程
        t2.interrupt();
    }
}
