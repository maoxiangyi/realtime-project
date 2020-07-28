package com.process.core.jobmanager;

import com.process.core.rpc.JobManagerHandlerService;
import com.process.core.util.NetworkUtil;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * @program: mywordcount
 * @description: 任务管理
 * @author: maoxiangyi
 * @create: 2020-07-03 20:50
 */
public class JobManager {
    public static void main(String[] args) throws TTransportException {
        NetworkUtil.startJobManagerService(9966);
    }

}
