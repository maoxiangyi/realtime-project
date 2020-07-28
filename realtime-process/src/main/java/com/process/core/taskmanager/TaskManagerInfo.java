package com.process.core.taskmanager;

import com.process.core.rpc.TaskManagerHandlerService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @program: mywordcount
 * @description:
 * @author: maoxiangyi
 * @create: 2020-07-08 14:11
 */
public class TaskManagerInfo {
    private String uuid;
    private String ip;
    private int port;
    private TaskManagerHandlerService.Client client;

    public TaskManagerInfo(String uuid, String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.uuid = uuid;
    }


    public void init(String ip, int port, int timeout) {
        TTransport transport = null;
        try {
            transport = new TSocket(ip, port, timeout);
            TProtocol protocol = new TBinaryProtocol(transport);
            client = new TaskManagerHandlerService.Client(protocol);
            transport.open();
        } catch (Exception e) {
            throw new RuntimeException("连接服务端失败", e);
        }
    }

    public TaskManagerHandlerService.Client getClient() {
        if (client==null){
            init(ip,port,30000);
        }
        return client;
    }

    public void setClient(TaskManagerHandlerService.Client client) {
        this.client = client;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "TaskManagerInfo{" +
                "uuid='" + uuid + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", client=" + client +
                '}';
    }
}
