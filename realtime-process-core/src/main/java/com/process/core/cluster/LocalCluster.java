package com.process.core.cluster;

import com.process.core.Component;
import com.process.core.Task;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: mywordcount
 * @description: 本地模式
 * @author: maoxiangyi
 * @create: 2020-07-02 19:48
 */
public class LocalCluster {
    public static void submit(String name, HashMap<String, Component> components) {
        HashMap<String, Task>  tasks = assign(components);
        executeTask(tasks);
    }

    private static void executeTask( HashMap<String, Task>  tasks) {
        ExecutorService threadPool = Executors.newFixedThreadPool(tasks.values().size());
        for (Task task : tasks.values()) {
            threadPool.submit(task);
        }
    }

    private static HashMap<String, Task> assign(HashMap<String, Component> components) {
        HashMap<String, Task> taskHashMap = new HashMap<String, Task>();
        for (Component component : components.values()) {
            Task task = new Task(component.getFunction());
            task.setName(component.getName());
            task.setLastTask(component.getLastName());
            task.setTaskType(component.getType());
            ArrayBlockingQueue<String> outputQueue = new ArrayBlockingQueue<String>(1000);
            task.setOutputQueue(outputQueue);
            task.setTaskObj(component.getFunction());
            taskHashMap.put(component.getName(), task);
        }
        for (Task task : taskHashMap.values()) {
            if (Component.PROCESS.equals(task.getTaskType())) {
                task.setInputQueue(taskHashMap.get(task.getLastTask()).getOutputQueue());
            } else {
                task.setInputQueue(new ArrayBlockingQueue<String>(1000));
            }
        }
        return taskHashMap;
    }
}
