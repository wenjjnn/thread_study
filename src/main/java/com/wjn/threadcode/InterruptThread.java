package com.wjn.threadcode;

/**
 * p39
 * Thread.interrupt()实例方法，作用是中断线程，线程中断并不会使线程立即退出，而是给线程发送一个通知，
 * 告知目标线程，有人希望你退出啦！至于目标线程接到通知后如何处理，则完全由目标线程自行决定。
 */
public class InterruptThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Thread is interrupted!");
                        break;
                    }
                    try {
                        Thread.sleep(2000); //线程被中断，则程序会抛出异常并清除中断标记
                    } catch (InterruptedException e) {
                        System.out.println("interrupted when sleep");
                        //需要再次中断自己，重置中断标记位，只有这么做，13行的中断检查才能发现当前线程已经被中断了
                        Thread.currentThread().interrupt();
                    }
                }
                Thread.yield();
            }
        };
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
    }
}
