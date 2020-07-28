package com.process.core.util;

import com.process.core.jobmanager.JobManagerHandlerServiceImpl;
import com.process.core.rpc.JobManagerHandlerService;
import com.process.core.rpc.TaskManagerHandlerService;
import com.process.core.taskmanager.TaskManagerHandlerServiceImpl;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.*;
import org.omg.PortableServer.POA;

import java.io.IOException;


/**
 * @program: mywordcount
 * @description: RPC公爵類
 * @author: maoxiangyi
 * @create: 2020-07-27 16:49
 */
public class NetworkUtil {
    public static void startJobManagerService(int port) throws TTransportException {
        // 处理器
        TProcessor tprocessor = new JobManagerHandlerService.Processor<JobManagerHandlerService.Iface>(new JobManagerHandlerServiceImpl());
        // 传输通道 - 非阻塞方式
        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
        // 异步IO，需要使用TFramedTransport，它将分块缓存读取。
        TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverTransport);
        tArgs.processor(tprocessor);
        tArgs.transportFactory(new TFramedTransport.Factory());
        // 使用高密度二进制协议
        tArgs.protocolFactory(new TCompactProtocol.Factory());
        // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
        TServer server = new TNonblockingServer(tArgs);
        System.out.println("Hello TNonblockingServer....");
        server.serve(); // 启动服务
    }

    public static JobManagerHandlerService.Client initJobManagerClient(String ip,int port,int timeout) throws IOException, TTransportException {
        TTransport transport = new TFramedTransport(new TSocket(ip, port, timeout));
        TProtocol protocol = new TCompactProtocol(transport);
        JobManagerHandlerService.Client client = new JobManagerHandlerService.Client(protocol);
        transport.open();
       return client;
    }


    public static void startTaskManagerService(int port) throws TTransportException {
        // 定义服务器使用的socket类型
        TNonblockingServerSocket tNonblockingServerSocket = new TNonblockingServerSocket(port);
        // 创建服务器参数
        THsHaServer.Args arg = new THsHaServer.Args(tNonblockingServerSocket).minWorkerThreads(10).maxWorkerThreads(20);
        // 请求处理器
        TaskManagerHandlerService.Processor<TaskManagerHandlerServiceImpl> processor = new TaskManagerHandlerService.Processor<>(new TaskManagerHandlerServiceImpl());
        // 配置传输数据的格式
        arg.protocolFactory(new TCompactProtocol.Factory());
        // 配置数据传输的方式
        arg.transportFactory(new TFramedTransport.Factory());
        // 配置处理器用来处理rpc请求
        arg.processorFactory(new TProcessorFactory(processor));
        // 本示例中使用半同步半异步方式的服务器模型
        TServer server = new THsHaServer(arg);
        // 启动服务
        server.serve();
    }

    public static TaskManagerHandlerService.Client initTaskManagerClient(String ip,int port,int timeout) throws IOException, TTransportException {
        TTransport transport = new TFramedTransport(new TSocket(ip, port, timeout));
        TProtocol protocol = new TCompactProtocol(transport);
        TaskManagerHandlerService.Client client = new TaskManagerHandlerService.Client(protocol);
        transport.open();
        return client;
    }
}
