package com.process.core.queue;

import com.process.core.job.AssignJob;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

/**
 * @program: mywordcount
 * @description: 先进先出调度器
 * @author: maoxiangyi
 * @create: 2020-07-27 20:28
 */
public class FIFOScheduler {
    private static ArrayBlockingQueue<AssignJob> queue = new ArrayBlockingQueue<AssignJob>(100);

    public static void submit(AssignJob assignJob) {
        try {
            queue.put(assignJob);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ArrayBlockingQueue<AssignJob> getQueue() {
        return queue;
    }
}
