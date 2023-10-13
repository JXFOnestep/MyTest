package com.company.LeetCode.twentythree_Oct.philosopher;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Xufeng Jiang
 * @date 2023年10月11日 10:34
 * @description 哲学家就餐问题
 * 破坏循环等待：所有资源按照顺序申请，给5个叉子编号，哲学家就餐时，必须要先申请编号小的叉子，再申请编号大的叉子
 */
public class DiningSolution1 {
    private ReentrantLock[] forks = new ReentrantLock[5];

    public DiningSolution1() {
        for (int i = 0; i < 5; i++) {
            forks[i] = new ReentrantLock();
        }
    }
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) {
        int lFork = philosopher;
        int rFork = (philosopher + 1) % 5;
        forks[Math.min(lFork, rFork)].lock(); //先拿编号小的叉子
        forks[Math.max(lFork, rFork)].lock(); //再拿编号大的叉子
        pickLeftFork.run();
        pickRightFork.run();
        eat.run();
        putLeftFork.run(); //用餐完成，放回叉子
        putRightFork.run();
        forks[Math.max(lFork, rFork)].unlock();
        forks[Math.min(lFork, rFork)].unlock();
    }
}
