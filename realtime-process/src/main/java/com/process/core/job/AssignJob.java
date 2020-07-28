package com.process.core.job;

/**
 * @program: mywordcount
 * @description: 等待分配的作业信息
 * @author: maoxiangyi
 * @create: 2020-07-27 20:30
 */
public class AssignJob {
    private String nameo;
    private String obj;
    private int works;

    public AssignJob() {
    }

    public AssignJob(String nameo, String obj, int works) {
        this.nameo = nameo;
        this.obj = obj;
        this.works = works;
    }

    public String getNameo() {
        return nameo;
    }

    public void setNameo(String nameo) {
        this.nameo = nameo;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public int getWorks() {
        return works;
    }

    public void setWorks(int works) {
        this.works = works;
    }
}
