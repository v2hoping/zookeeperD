//package com.owl.zookeeper;
//
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.ZooKeeper;
//
///**
// * Created by wanghouping on 2018/3/14.
// *
// * @author houping wang
// */
//public class BasicsExistData {
//
//    private static String path = "/whpGet";
//
//    /**
//     * 验证getData监听.
//     */
//    public static void main(String[] args) throws KeeperException, InterruptedException {
//        BasicsApi basicsApi = new BasicsApi();
//        ZooKeeper zk = basicsApi.getZk();
//        zk.create(path, "whpGetData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        //创建子节点(不监听)
//        zk.exists(path, true);
//        zk.create("/whpGet/one", "one".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        //修改子节点数据(不监听)
//        zk.exists(path, true);
//        zk.setData("/whpGet/one", "two".getBytes(), -1);
//        //修改节点数据(监听)
//        zk.exists(path, true);
//        zk.setData(path, "whpGetData1".getBytes(), -1);
//        //删除子节点(不监听)
//        zk.exists(path, true);
//        zk.delete("/whpGet/one", -1);
//        //删除节点(监听)
//        zk.exists(path, true);
//        zk.delete(path, -1);
//    }
//}
