package com.process.core.jobmanager;

import com.process.core.Component;
import com.process.core.Task;
import com.process.core.job.AssignJob;
import com.process.core.queue.FIFOScheduler;
import com.process.core.rpc.JobManagerHandlerService;
import com.process.core.taskmanager.TaskManagerInfo;
import com.process.core.util.SerializableUtil;
import org.apache.thrift.TException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @program: thriftdemo
 * @description: master具体实现类
 * @author: maoxiangyi
 * @create: 2020-07-06 14:27
 * <p>
 * https://www.cnblogs.com/duanxz/p/5516558.html
 */
public class JobManagerHandlerServiceImpl implements JobManagerHandlerService.Iface {


    public JobManagerHandlerServiceImpl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    AssignJob assignJob = null;
                    try {
                        assignJob = FIFOScheduler.getQueue().take();
                        execute(assignJob);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (TException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private HashMap<String, TaskManagerInfo> taskManagerInfoHashMap = new HashMap<>();

    @Override
    public String submitJob(String name, String obj, int works) throws TException {
        FIFOScheduler.submit(new AssignJob(name, obj, works));
        return "success";
    }

    public void execute(AssignJob assignJob) throws IOException, ClassNotFoundException, TException {

        loadClass();

        //反序列用户编写的作业信息
        HashMap<String, Component> components = (HashMap<String, Component>) SerializableUtil.deSerializable(assignJob.getObj());
        //获得当前有多少个TaskManager
        int taskManagerSize = taskManagerInfoHashMap.size();
        //根据works 数量，选择可以使用的taskmanager
        if (assignJob.getWorks() >= taskManagerSize) {
            //运行所有taskmanager
            ArrayList<HashMap<String, Task>> workInfos = assign(components, taskManagerSize);
            int i = 0;
            for (TaskManagerInfo value : taskManagerInfoHashMap.values()) {
                String tasks = SerializableUtil.serializable(workInfos.get(i));
                value.getClient().assginTask(tasks);
                i++;
            }
        } else {
            //todo
        }

    }

    private void loadClass() {
        loadJar("G:\\code\\realtime-project\\realtime-process-demo\\target\\realtime-process-demo-1.0.jar");
    }
    public static void loadJar(String path){
        // 系统类库路径
        File libPath = new File(path);

        // 获取所有的.jar和.zip文件
        File[] jarFiles = libPath.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar") || name.endsWith(".zip");
            }
        });

        if (jarFiles != null) {
            // 从URLClassLoader类中获取类所在文件夹的方法
            // 对于jar文件，可以理解为一个存放class文件的文件夹
            Method method = null;
            try {
                method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            } catch (NoSuchMethodException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            boolean accessible = method.isAccessible();     // 获取方法的访问权限
            try {
                if (accessible == false) {
                    method.setAccessible(true);     // 设置方法的访问权限
                }
                // 获取系统类加载器
                URLClassLoader classLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();;
                for (File file : jarFiles) {
                    try {
                        URL url = file.toURI().toURL();
                        method.invoke(classLoader, url);
                        System.out.println("读取jar文件成功"+file.getName());
                    } catch (Exception e) {
                        System.out.println("读取jar文件失败");
                    }
                }
            } finally {
                method.setAccessible(accessible);
            }
        }
    }

    @Override
    public String registerTaskManager(String ip, int port, String uuid) throws TException {
        registry(new TaskManagerInfo(uuid, ip, port));
        System.out.println(taskManagerInfoHashMap);
        return "OK";
    }

    private void initClient() {

    }

    private void registry(TaskManagerInfo taskManagerInfo) {
        taskManagerInfoHashMap.put(taskManagerInfo.getUuid(), taskManagerInfo);
    }


    private static ArrayList<HashMap<String, Task>> assign(HashMap<String, Component> components, int workNum) {

        ArrayList<HashMap<String, Task>> workInfo = initworks(workNum);
        int id = 0;
        for (Component component : components.values()) {
            for (int i = 0; i < component.getParallelism(); i++) {
                Task task = new Task(id, component.getFunction(), component.getType(), component.getLastName(), component.getName());
                int whichWork = id % workNum;
                HashMap<String, Task> taskHashMap = workInfo.get(whichWork);
                taskHashMap.put(id + "", task);
                id++;
            }
        }
        return workInfo;
    }

    private static ArrayList<HashMap<String, Task>> initworks(int workNum) {
        ArrayList<HashMap<String, Task>> arrayList = new ArrayList<HashMap<String, Task>>(workNum);
        for (int i = 0; i < workNum; i++) {
            HashMap<String, Task> taskHashMap = new HashMap<>();
            arrayList.add(taskHashMap);
        }
        return arrayList;
    }
}
