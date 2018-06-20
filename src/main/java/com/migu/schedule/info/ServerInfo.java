package com.migu.schedule.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServerInfo
{
    int nodeId;
    
   
    public int getNodeId()
    {
        return nodeId;
    }



    public void setNodeId(int nodeId)
    {
        this.nodeId = nodeId;
    }

    List<TaskInfo> center = new ArrayList<TaskInfo>();
    Map<Integer, Integer> consumptions = new HashMap<Integer, Integer>();
    int allconsumptions;
    
    public int getAllconsumptions()
    {
        Set<Integer> set = consumptions.keySet();
        for (Integer integer : set)
        {
            allconsumptions+=consumptions.get(integer);
        }
        return allconsumptions;
    }

    

    public ServerInfo(int nodeId2)
    {
        nodeId = nodeId2;
    }
    
    public void addTask(TaskInfo taskInfo, int consumption)
        throws Exception
    {
        int taskid = taskInfo.getTaskId();
        taskInfo.setNodeId(nodeId);
        for (TaskInfo taskInfo2 : center)
        {
            int temp = taskInfo2.getTaskId();
            if (temp == taskid)
            {
                throw new Exception();
            }
        }
        consumptions.put(taskid, consumption);
        center.add(taskInfo);
    }
    
    public void deleteTask(TaskInfo taskInfo)
        throws Exception
    {
        int taskid = taskInfo.getTaskId();
        taskInfo.setNodeId(nodeId);
        for (TaskInfo taskInfo2 : center)
        {
            int temp = taskInfo2.getTaskId();
            if (temp == taskid)
            {
                center.remove(taskInfo2);
                consumptions.remove(temp);
                return;
            }
        }
     
        throw new Exception();
        
    }

    public List<TaskInfo> getCenter()
    {
        return center;
    }

    public void setCenter(List<TaskInfo> center)
    {
        this.center = center;
    }

    public Map<Integer, Integer> getConsumptions()
    {
        return consumptions;
    }

    public void setConsumptions(Map<Integer, Integer> consumptions)
    {
        this.consumptions = consumptions;
    }



    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ServerInfo [nodeId=");
        builder.append(nodeId);
        builder.append(", center=");
        builder.append(center);
        builder.append(", consumptions=");
        builder.append(consumptions);
        builder.append(", allconsumptions=");
        builder.append(allconsumptions);
        builder.append("]");
        return builder.toString();
    }
    
    
}
