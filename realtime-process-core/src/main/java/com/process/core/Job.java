package com.process.core;

import com.process.core.cluster.Cluster;
import com.process.core.cluster.LocalCluster;
import org.apache.thrift.TException;

import java.io.IOException;
import java.util.HashMap;

/**
 * @program: mywordcount
 * @description: 任务描述
 * @author: maoxiangyi
 * @create: 2020-07-02 14:54
 */
public class Job {
    private String name;
    private int jvmNum;
    private HashMap<String, Component> components = new HashMap<String, Component>();

    public int getJvmNum() {
        return jvmNum;
    }

    public void setJvmNum(int jvmNum) {
        this.jvmNum = jvmNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Component setSource(String name, UserFunction userFunction) {
        Component component = new Component(name, Component.SOURCE, userFunction);
        components.put(name, component);
        return component;
    }

    public Component setProcess(String name, UserFunction userFunction) {
        Component component = new Component(name, Component.PROCESS, userFunction);
        components.put(name, component);
        return component;
    }

    public Component setSource(String name, UserFunction userFunction,int parallelism) {
        Component component = new Component(name, Component.SOURCE, userFunction);
        component.setParallelism(parallelism);
        components.put(name, component);
        return component;
    }

    public Component setProcess(String name, UserFunction userFunction,int parallelism) {
        Component component = new Component(name, Component.PROCESS, userFunction);
        component.setParallelism(parallelism);
        components.put(name, component);
        return component;
    }

    public void submit() throws IOException, TException {
        if (jvmNum == 0) {
            LocalCluster.submit(name, components);
        } else {
           Cluster cluster =  new Cluster();
           cluster.submit(name,components,jvmNum);
        }
    }


}
