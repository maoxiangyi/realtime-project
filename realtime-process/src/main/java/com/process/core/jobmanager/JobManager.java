package com.process.core.jobmanager;

import com.process.core.util.NetworkUtil;
import org.apache.thrift.transport.TTransportException;

public class JobManager {
    public static void main(String[] args) throws TTransportException {
        NetworkUtil.startJobManagerService(9999);
    }

}
