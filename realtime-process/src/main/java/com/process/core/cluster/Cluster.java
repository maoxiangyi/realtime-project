package com.process.core.cluster;

import com.process.core.Component;
import com.process.core.rpc.JobManagerHandlerService;
import com.process.core.util.NetworkUtil;
import com.process.core.util.SerializableUtil;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.util.HashMap;

/**
 * @program: mywordcount
 * @description:
 * @author: maoxiangyi
 * @create: 2020-07-02 19:52
 */
public class Cluster {
    private JobManagerHandlerService.Client client;

    public void submit(String name, HashMap<String, Component> components, int jvmNum) throws IOException, TException {
        if (client == null) {
            client =   NetworkUtil.initJobManagerClient("127.0.0.1",9999,30000);
        }
        String obj = SerializableUtil.serializable(components);
        String s = client.submitJob(name,obj,jvmNum);
        System.out.println(s);
    }

}
