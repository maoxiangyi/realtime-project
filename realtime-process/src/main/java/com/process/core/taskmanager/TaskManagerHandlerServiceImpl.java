package com.process.core.taskmanager;

import com.process.core.Component;
import com.process.core.Task;
import com.process.core.rpc.JobManagerHandlerService;
import com.process.core.rpc.TaskManagerHandlerService;
import com.process.core.util.NetworkUtil;
import com.process.core.util.SerializableUtil;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: thriftdemo
 * @description: master具体实现类
 * @author: maoxiangyi
 * @create: 2020-07-06 14:27
 * <p>
 * https://www.cnblogs.com/duanxz/p/5516558.html
 */
public class TaskManagerHandlerServiceImpl implements TaskManagerHandlerService.Iface {
    private JobManagerHandlerService.Client client;

    public TaskManagerHandlerServiceImpl() {
        try {
            client = NetworkUtil.initJobManagerClient("127.0.0.1", 9999, 30000);
        } catch (Exception e) {
            throw new RuntimeException("连接JobManager失败!", e);
        }
        try {
            String result = client.registerTaskManager("127.0.0.1", 8888, UUID.randomUUID().toString().replaceAll("-", ""));
            System.out.println(result);
        } catch (TException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public String assginTask(String components) throws TException {
        try {
            HashMap<String, Task> taskHashMap = (HashMap<String, Task>) SerializableUtil.deSerializable(components);
            for (Task task : taskHashMap.values()) {
                ArrayBlockingQueue<String> outputQueue = new ArrayBlockingQueue<String>(1000);
                task.setOutputQueue(outputQueue);
            }
            for (Task task : taskHashMap.values()) {
                if (Component.PROCESS.equals(task.getTaskType())) {
                    task.setInputQueue(taskHashMap.get(task.getLastTask()).getOutputQueue());
                } else {
                    task.setInputQueue(new ArrayBlockingQueue<String>(1000));
                }
            }
            executeTask(taskHashMap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void executeTask(HashMap<String, Task> tasks) {
        ExecutorService threadPool = Executors.newFixedThreadPool(tasks.values().size());
        for (Task task : tasks.values()) {
            threadPool.submit(task);
        }
    }

    @Override
    public String dispatchMessage(String jobName, int taskId, String message) throws TException {
        return null;
    }
}
