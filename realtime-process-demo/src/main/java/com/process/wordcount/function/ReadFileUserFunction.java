package com.process.wordcount.function;

import com.process.core.UserFunction;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @program: mywordcount
 * @description:
 * @author: maoxiangyi
 * @create: 2020-07-02 18:52
 */
public class ReadFileUserFunction implements UserFunction, Serializable {
    private static final long serialVersionUID = -8408815782408055855L;

    public void open() {
    }

    public void execute(String inputMessage, ArrayBlockingQueue dataCollector) throws IOException {
            BufferedReader fileReader =
                    new BufferedReader(new FileReader("d://wordcount//wordcount.txt"));
            String sentence = null;
            while ((sentence = fileReader.readLine()) != null) {
                try {
                    dataCollector.put(sentence);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

    public void close() {
    }
}
