package com.process.wordcount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @program: mywordcount
 * @description: 单词计数
 * @author: maoxiangyi
 * @create: 2020-07-01 17:51
 */
public class WordCount {
    public static void main(String[] args) throws Exception {
        //  读取文本中的文本，并保存到消息队列中
        readTextWithFile();
        // 从消息队列中读取文本，并切分出每个单词
        splitWords();
        // 统计单词出现的次数，并打印在控制台
        wordCountAndPrint();
    }

    //创建一个消息队列用来保存从文件中读取的句子信息
    private static ArrayBlockingQueue<String> sentenceQueue
            = new ArrayBlockingQueue<String>(1000);

    /**
     * 读取文本中的文本，并保存到消息队列中
     *
     * @throws Exception
     */
    private static void readTextWithFile() throws Exception {
        BufferedReader fileReader =
                new BufferedReader(new FileReader("d://wordcount//wordcount.txt"));
        String sentence = null;
        while ((sentence = fileReader.readLine()) != null) {
            sentenceQueue.put(sentence);
        }
    }

    //创建一个消息队列用来保存从文件中读取的句子信息
    private static ArrayBlockingQueue<String> wordQueue
            = new ArrayBlockingQueue<String>(1000);

    /**
     * 从消息队列中读取文本，并切分出每个单词
     */
    private static void splitWords() {
        while (sentenceQueue.size()>0) {
            try {
                String sentence = sentenceQueue.take();
                String[] words = sentence.split(" ");
                for (String word : words) {
                    wordQueue.put(word);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static HashMap<String, Integer> wordCountMap =
            new HashMap<String, Integer>();

    /**
     * 读物
     *
     * @throws InterruptedException
     */
    private static void wordCountAndPrint() throws InterruptedException {
        while (wordQueue.size()>0) {
            String word = wordQueue.take();
            if (wordCountMap.containsKey(word)) {
                Integer oldValue = wordCountMap.get(word);
                wordCountMap.put(word, oldValue + 1);
            } else {
                wordCountMap.put(word, 1);
            }
            printMap();
        }
    }

    private static void printMap() {
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            System.out.printf("单词:%s,数量%s\n", entry.getKey(), entry.getValue());
        }
        System.out.println("-------");
    }
}
