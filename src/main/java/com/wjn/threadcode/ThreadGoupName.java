package com.wjn.threadcode;

/**
 * p53
 * 线程组的使用
 */
public class ThreadGoupName implements Runnable{

    public static void main(String[] args) {
        ThreadGroup tg = new ThreadGroup("PrintGroup");
        Thread t1 = new Thread(tg, new ThreadGoupName(), "T1");
        Thread t2 = new Thread(tg, new ThreadGoupName(), "T2");
        t1.start();
        t2.start();
        // activeCount() 可以获得活动线程的总数，但由于线程是动态的，因此这个值只是一个估计值
        System.out.println(tg.activeCount());
        // list() 可以打印这个线程组中所有的线程信息
        tg.list();
    }

    @Override
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName()
                + "-"
                + Thread.currentThread().getName();
        while (true) {
            System.out.println("I am " + groupAndName);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
