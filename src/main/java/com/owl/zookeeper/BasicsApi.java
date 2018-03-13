package com.owl.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.proto.WatcherEvent;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by wanghouping on 2018/3/13.
 * 基础Api
 * @author houping wang
 */
public class BasicsApi {

    private static final int SESSION_TIMEOUT = 60000;

    private ZooKeeper zk = null;

    private String root = "/whp";

    public BasicsApi() throws IOException {
        zk = new ZooKeeper("172.16.120.134:16300,172.16.120.135:16300,172.16.120.136:16300", SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent event) {
                String path = event.getPath();
                Event.KeeperState state = event.getState();
                Event.EventType type = event.getType();
                WatcherEvent wrapper = event.getWrapper();
                System.out.println("监听:path:" + path + ",state:" + state + ",type:" + type + ",wrapper:" + wrapper);
            }
        });
    }

    public void delete() throws KeeperException, InterruptedException {
//        zk.delete("/testRootPath/testChildPathTwo",-1);
//        zk.delete("/testRootPath/testChildPathOne",-1);
        // 删除父目录节点
        zk.delete("/testRootPath",-1);
        // 关闭连接
        zk.close();
    }

    public void run() throws KeeperException, InterruptedException {
        // 创建一个目录节点
        zk.create("/testRootPath", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        // 取出子目录节点列表
        System.out.println(zk.getChildren("/testRootPath",true));//TODO:监控子节点的变化，删除、创建
        // 创建一个子目录节点
        zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/testRootPath",true,null)));//TODO:监控当前节点变化，数据变化、存在变化
        // 修改子目录节点数据
        zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1);
//        System.out.println("目录节点状态：["+zk.exists("/testRootPath",true)+"]");
        // 创建另外一个子目录节点
        zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/testRootPath/testChildPathTwo",true,null)));
        // 删除子目录节点
        zk.delete("/testRootPath/testChildPathTwo",-1);
        zk.delete("/testRootPath/testChildPathOne",-1);
        // 删除父目录节点
        zk.delete("/testRootPath",-1);
        // 关闭连接
        zk.close();
    }

    public void run1() throws KeeperException, InterruptedException {
        // 创建一个目录节点
        zk.create("/testRootPath", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        // 创建一个子目录节点
        zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/testRootPath/testChildPathOne", true, null)));
        zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1);
        zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne1".getBytes(),-1);
    }




}
