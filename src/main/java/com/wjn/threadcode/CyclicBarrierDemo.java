package com.wjn.threadcode;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * p89
 */
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclic;

        Soldier(CyclicBarrier cyclic, String soldierName) {
            this.cyclic = cyclic;
            this.soldier = soldierName;
        }

        @Override
        public void run() {
            try {
                //等待所有士兵到齐，集合完毕意味着CyclicBarrier的一次计数完成
                cyclic.await();
                doWork();
                //等待所有士兵完成工作
                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        void doWork() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier + ":任务完成");
        }
    }
    public static class BarrierRun implements Runnable {
        boolean flag;
        int N;
        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("司令:[士兵" + N + "个，任务完成!");
            }else {
                System.out.println("司令:[士兵" + N + "个，集合完毕!");
                flag = true;
            }

        }
    }

    public static void main(String[] args) {
        final  int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        // 设置计数器为10，要求在计数器达到指标时，执行BarrierRun的run()方法
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));
        System.out.println("集合队伍!");
        for (int i = 0;i < N; ++i) {
            System.out.println("士兵 " + i +" 报道!");
            allSoldier[i] = new Thread(new Soldier(cyclic,"士兵 " + i));
            allSoldier[i].start();
        }
    }


}
