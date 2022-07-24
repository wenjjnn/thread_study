package com.wjn.threadcode;

/**
 * p49
 * 简单的join()实例
 * 如果不使用join()等待AddThread，那么得到的i很可能0或者一个非常小的数字，
 * 因为AddThread还没开始执行，i的值就已经被输出了。但是在使用join()方法后，表示主线程愿意等待AddThread执行完毕
 */
public class JoinMain {
    public static  int i = 0;
    static class AddThread extends Thread {
        @Override
        public void run() {
            for (i = 0; i < 1000000; i++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread at = new AddThread();
        at.start();
        at.join();
        System.out.println(i);
    }
}
