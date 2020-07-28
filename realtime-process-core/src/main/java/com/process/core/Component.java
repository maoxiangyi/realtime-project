package com.process.core;

import java.io.Serializable;

/**
 * @program: mywordcount
 * @description: 组件
 * @author: maoxiangyi
 * @create: 2020-07-02 18:56
 */
public class Component implements Serializable {

    private static final long serialVersionUID = -5054766216159319707L;
    private String name;
    private String type;
    private int parallelism;
    private UserFunction function;
    private String lastName;

    public static final String PROCESS = "PROCESS";
    public static final String SOURCE = "SOURCE";


    public Component(String name, String type, UserFunction userFunction) {
        this.name = name;
        this.function = userFunction;
        this.type = type;
        this.parallelism = 1;
    }

    public Component(String name, String type, UserFunction userFunction, int parallelism) {
        this.name = name;
        this.function = userFunction;
        this.type = type;
        this.parallelism = parallelism;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getParallelism() {
        return parallelism;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public UserFunction getFunction() {
        return function;
    }

    public void setFunction(UserFunction function) {
        this.function = function;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}

