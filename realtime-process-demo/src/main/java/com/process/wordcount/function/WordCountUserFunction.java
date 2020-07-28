package com.process.wordcount.function;

import com.process.core.UserFunction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @program: mywordcount
 * @description:
 * @author: maoxiangyi
 * @create: 2020-07-02 18:52
 */
public class WordCountUserFunction implements UserFunction, Serializable {

    private static final long serialVersionUID = 4335611239394613911L;
    private static HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();

    public void open() {
    }

    public void execute(String inputMessage, ArrayBlockingQueue dataCollector) {
        if (wordCountMap.containsKey(inputMessage)) {
            Integer oldValue = wordCountMap.get(inputMessage);
            wordCountMap.put(inputMessage, oldValue + 1);
        } else {
            wordCountMap.put(inputMessage, 1);
        }
        printMap();
    }

    private static void printMap() {
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            System.out.printf("单词:%s,数量%s\n", entry.getKey(), entry.getValue());
        }
        System.out.println("-------");
    }

    public void close() {
    }


}
