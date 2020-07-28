package com.process.core.taskmanager;

import com.process.core.rpc.JobManagerHandlerService;
import com.process.core.rpc.TaskManagerHandlerService;
import com.process.core.util.NetworkUtil;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.UUID;

/**
 * @program: mywordcount
 * @description:
 * @author: maoxiangyi
 * @create: 2020-07-08 14:11
 */
public class TaskManager {

    public static void main(String[] args) throws TTransportException {
        NetworkUtil.startTaskManagerService(8888);
    }

}
