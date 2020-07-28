package com.process.wordcount;

import com.process.core.Job;
import com.process.wordcount.function.ReadFileUserFunction;
import com.process.wordcount.function.SplitWordUserFunction;
import com.process.wordcount.function.WordCountUserFunction;
import org.apache.thrift.TException;

import java.io.IOException;

/**
 * @program: mywordcount
 * @description: 单词计数任务
 * @author: maoxiangyi
 * @create: 2020-07-02 14:51
 */
public class WordCountMain {
    public static void main(String[] args) throws IOException, TException {
        //创建一个Job 用来做单词技术
        Job job = new Job();
        //设置Job的名称
        job.setName("wordcount");
        //设置Job从哪类读取数据
        job.setSource("readFile",new ReadFileUserFunction(),2);
        // 读取数据收使用SplitWordUserFunction进行句子切割
        job.setProcess("split",new SplitWordUserFunction(),2).setLastName("readFile");
        //句子切割成单词之后，使用WordCountUserFunction进行单词计数
        job.setProcess("wordCount",new WordCountUserFunction(),2).setLastName("split");

        //选择集群模式
        job.setJvmNum(2);
        //提交任务
        job.submit();
    }
}
