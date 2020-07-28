package com.process.wordcount.function;

import com.process.core.UserFunction;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @program: mywordcount
 * @description:
 * @author: maoxiangyi
 * @create: 2020-07-02 18:52
 */
public class SplitWordUserFunction implements UserFunction, Serializable {
    private static final long serialVersionUID = 7895882539013650872L;

    public void open() {
    }

    public void execute(String inputMessage, ArrayBlockingQueue dataCollector) {
        String[] words = inputMessage.split(" ");
        for (String word : words) {
            try {
                dataCollector.put(word);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
    }
}
