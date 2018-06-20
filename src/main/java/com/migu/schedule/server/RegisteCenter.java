package com.migu.schedule.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.migu.schedule.info.ServerInfo;
import com.migu.schedule.info.TaskInfo;

public class RegisteCenter
{
    static Map<Integer, ServerInfo> center = new HashMap<Integer, ServerInfo>();
    
    static List<TaskInfo> taskcenter = new ArrayList<TaskInfo>();
    static List<TaskInfo> sorttaskcenter = new ArrayList<TaskInfo>();
    
    static  Map<Integer, Integer> consumptions = new HashMap<Integer, Integer>();
    
    public static void register(int nodeId)
        throws Exception
    {
        ServerInfo temp = center.get(nodeId);
        if (temp != null)
        {
            throw new Exception();
        }
        else
        {
            center.put(nodeId, new ServerInfo(nodeId));
        }
    }
    
    public static void logout(int nodeId)
        throws Exception
    {
        ServerInfo temp = center.get(nodeId);
        if (temp == null)
        {
            throw new Exception();
        }
        else
        {
            center.remove(nodeId);
        }
    }
    
    public static void clear()
    {
        center.clear();
        taskcenter.clear();
       
    }
    
    public static ServerInfo getServer(int nodeId)
    {
        return center.get(nodeId);
    }
    
    public static List<ServerInfo> getAllServers()
    {
        Set<Integer> set = center.keySet();
        List<ServerInfo> servers = new ArrayList<ServerInfo>();
        for (Integer integer : set)
        {
            ServerInfo server = center.get(integer);
            servers.add(server);
        }
        return servers;
    }
    
    public static  void  getallTask()
     {
        List<ServerInfo> servers = RegisteCenter.getAllServers();
        System.out.println("getallTask:"+servers);
        for (ServerInfo serverInfo : servers)
        {
            sorttaskcenter.addAll(serverInfo.getCenter());
        }
        
        
    }

    public static List<TaskInfo> getSorttaskcenter()
    {
        return sorttaskcenter;
    }

    public static void setSorttaskcenter(List<TaskInfo> sorttaskcenter)
    {
        RegisteCenter.sorttaskcenter = sorttaskcenter;
    }

    public static List<TaskInfo> getTaskcenter()
    {
        return taskcenter;
    }

    public static void addTask(TaskInfo taskInfo, int consumption)
        throws Exception
    {
        int taskid = taskInfo.getTaskId();
        for (TaskInfo taskInfo2 : taskcenter)
        {
            int temp = taskInfo2.getTaskId();
            if (temp == taskid)
            {
                throw new Exception();
            }
        }
        consumptions.put(taskid, consumption);
        taskcenter.add(taskInfo);
    }
    
    public static void deleteTask(TaskInfo taskInfo)
        throws Exception
    {
        int taskid = taskInfo.getTaskId();
        for (TaskInfo taskInfo2 : taskcenter)
        {
            int temp = taskInfo2.getTaskId();
            if (temp == taskid)
            {
                taskcenter.remove(taskInfo2);
                consumptions.remove(temp);
                return;
            }
        }
     
        throw new Exception();
        
    }

    public static Map<Integer, Integer> getConsumptions()
    {
        return consumptions;
    }

   

    
}
