package com.process.core;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;


public interface UserFunction {
    void open();
    void execute(String inputMessage, ArrayBlockingQueue dataCollector) throws IOException;
    void close();
}
