package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.ServerInfo;
import com.migu.schedule.info.TaskInfo;
import com.migu.schedule.server.RegisteCenter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/*
*类名和方法不能修改
 */
public class Schedule {

    ServerInfo server;

    public int init() {
        //初始化注册中心
        server = null;
        RegisteCenter.clear();
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {
        if(nodeId<=0)
        {
            return ReturnCodeKeys.E004;
        }
        try
        {
            RegisteCenter.register(nodeId);
            server = RegisteCenter.getServer(nodeId);
        }
        catch (Exception e)
        {
            return ReturnCodeKeys.E005;
        }
        return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {
        if(nodeId<=0)
        {
            return ReturnCodeKeys.E004;
        }
        try
        {
            RegisteCenter.logout(nodeId);
            server = null;
        }
        catch (Exception e)
        {
            return ReturnCodeKeys.E007;
        }
        return ReturnCodeKeys.E006;
    }


    public int addTask(int taskId, int consumption) {
        // TODO  根据业务规则添加任务
        if(taskId<=0)
        {
            return ReturnCodeKeys.E009;
        }
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskId(taskId);
        
        try
        {
            RegisteCenter.addTask(taskInfo, consumption);
        }
        catch (Exception e)
        {
            return ReturnCodeKeys.E010;
        }
        
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
        if(taskId<=0)
        {
            return ReturnCodeKeys.E009;
        }
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskId(taskId);
        try
        {
            RegisteCenter.deleteTask(taskInfo);
        }
        catch (Exception e)
        {
            return ReturnCodeKeys.E012;
        }
        
        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold) {
        if(threshold<0) 
        {
            return ReturnCodeKeys.E002;
        }
        List<TaskInfo> tasks = RegisteCenter.getTaskcenter();
        System.out.println(tasks);
        List<ServerInfo> serverInfos = RegisteCenter.getAllServers();
        System.out.println(serverInfos);
        Collections.sort(serverInfos, new Comparator<ServerInfo>()
        {

            public int compare(ServerInfo o1, ServerInfo o2)
            {
                if(o1.getNodeId()<o2.getNodeId()) 
                {
                    return -1;
                }
                if(o1.getNodeId()>o2.getNodeId()) 
                {
                    return 1;
                }
                return 0;
            }
        });
        Map<Integer, Integer> consumptions = RegisteCenter.getConsumptions();
        if(tasks!=null&&tasks.size()>0) 
        {
            for (TaskInfo taskInfo : tasks)
            {
                int id = -1;
                int cha = 0;
                for (ServerInfo serverInfo : serverInfos)
                {
                    int all = serverInfo.getAllconsumptions();
                    int tempcha = all-threshold;
                    if(id==-1||tempcha<cha) 
                    {
                        cha = tempcha;
                        try
                        {
                            serverInfo.addTask(taskInfo, consumptions.get(taskInfo.getTaskId()));
                            if(id!=-1) 
                            {
                                RegisteCenter.getServer(id).deleteTask(taskInfo);;
                            }
                            id = serverInfo.getNodeId();
                        }
                        catch (Exception e)
                        {
                            continue;
                        }
                    }
                   
                }
            }
        }
        System.out.println(serverInfos);
        return ReturnCodeKeys.E013;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        if(tasks == null) 
        {
            return ReturnCodeKeys.E016;
        }
        RegisteCenter.getallTask();
        tasks.addAll(RegisteCenter.getSorttaskcenter());
        System.out.println("tasks = "+tasks);
        Collections.sort(tasks, new Comparator<TaskInfo>()
        {

            public int compare(TaskInfo o1, TaskInfo o2)
            {
                if(o1.getTaskId()<o2.getTaskId()) 
                {
                    return -1;
                }
                if(o1.getTaskId()>o2.getTaskId()) 
                {
                    return 1;
                }
                return 0;
            }
        });
        System.out.println("tasks = "+tasks);
        return ReturnCodeKeys.E015;
    }

}
