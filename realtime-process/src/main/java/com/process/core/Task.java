package com.process.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @program: mywordcount
 * @description: 任务类
 * @author: maoxiangyi
 * @create: 2020-07-02 14:32
 */
public class Task implements Runnable,Serializable {


    private ArrayBlockingQueue<String> inputQueue;
    private ArrayBlockingQueue<String> outputQueue;
    private UserFunction taskObj;
    private String taskType;
    private String lastTask;
    private String name;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastTask() {
        return lastTask;
    }

    public void setLastTask(String lastTask) {
        this.lastTask = lastTask;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Task() {
    }

    public Task(UserFunction function) {
        this.taskObj = function;
    }

    public Task(int id, UserFunction taskObj, String taskType, String lastTask, String name) {
        this.taskObj = taskObj;
        this.taskType = taskType;
        this.lastTask = lastTask;
        this.name = name;
        this.id = id;
    }

    public void run() {
        if (Component.SOURCE.equals(taskType)) {
            try {
                taskObj.execute("", outputQueue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            while (true) {
                try {
                    String element = inputQueue.take();
                    taskObj.execute(element, outputQueue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public ArrayBlockingQueue<String> getInputQueue() {
        return inputQueue;
    }

    public void setInputQueue(ArrayBlockingQueue<String> inputQueue) {
        this.inputQueue = inputQueue;
    }

    public ArrayBlockingQueue<String> getOutputQueue() {
        return outputQueue;
    }

    public void setOutputQueue(ArrayBlockingQueue<String> outputQueue) {
        this.outputQueue = outputQueue;
    }

    public UserFunction getTaskObj() {
        return taskObj;
    }

    public void setTaskObj(UserFunction taskObj) {
        this.taskObj = taskObj;
    }
}
