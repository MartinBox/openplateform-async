package com.openplateform.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by root on 16-9-17.
 */
public class ConditionTest {
    private Object[] items;
    private int addIndex, removeIndex, count;
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public ConditionTest(int size) {
        System.out.println("init size " + size);
        items = new Object[size];
    }

    /**
     * 添加一个元素，如果数组已满，则添加线程进入等待状态，直到有“空位”
     *
     * @param obj
     * @throws InterruptedException
     */
    public void add(Object obj) throws InterruptedException {
        lock.lock();
        String threadName = Thread.currentThread().getName();
        try {
            while (count == items.length) {
                System.out.println(threadName + ":quence is full,awaiting remove");
                // 进入等待状态，直到被通知（signal）或中断
                notFull.await();
            }
            items[addIndex] = obj;
            if (++addIndex == items.length) {
                addIndex = 0;
            }
            ++count;
            StringBuffer buff = new StringBuffer();
            for (Object item : items) {
                buff.append(item + " ");
            }
            System.out.println(threadName + ":add success,current item ：" + buff.toString());

            // 唤醒一个等待在Condition上的线程
            notEmpty.signal();

        } finally {
            lock.unlock();
        }
    }

    /**
     * 由头部删除一个元素，如果数组为空，则删除线程进入等待状态，只有有新添加元素
     *
     * @return
     * @throws InterruptedException
     */
    public void remove() throws InterruptedException {
        lock.lock();
        String threadName = Thread.currentThread().getName();
        try {
            while (count == 0) {
                System.out.println(threadName + ":quence is empty,awaiting add");
                notEmpty.await();
            }
            items[removeIndex] = null;
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            --count;
            StringBuffer buff = new StringBuffer();
            for (Object item : items) {
                buff.append(item + " ");
            }
            System.out.println(threadName + ":remove success,current item ：" + buff.toString());
            notFull.signal();
        } finally {
            lock.unlock();
        }

    }

}
