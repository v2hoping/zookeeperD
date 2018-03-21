package com.owl.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.proto.WatcherEvent;

import java.io.IOException;

/**
 * Created by wanghouping on 2018/3/19.
 *
 *
 *
 * @author houping wang
 */
public class WatcherBack implements Watcher, AsyncCallback.StatCallback, Runnable {

    private ZooKeeper zk;

    private static final int SESSION_TIMEOUT = 30000;

    private Thread thread;

    private boolean dead = false;

    private String znode;

    public WatcherBack(String znode) {
        try {
            this.znode = znode;
            zk = new ZooKeeper("172.16.120.134:16300,172.16.120.135:16300,172.16.120.136:16300", SESSION_TIMEOUT, this);
            zk.exists(znode, true, this, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WatcherBack watcherBack = new WatcherBack("/whp");
        watcherBack.run();
        watcherBack.printlnStr();
    }

    public void process(WatchedEvent event) {
        System.out.println("path:" + event.getPath() + ",state:" + event.getState());
        String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case SyncConnected:
                    break;
                case Expired:
                    dead = true;
                    synchronized (o) {
                        o.notifyAll();
                    }
                    break;
            }
        } else {
            if (path != null && path.equals(znode)) {
                zk.exists(znode, true, this, null);
            }
        }
    }

    public void processResult(int rc, String path, Object ctx, Stat stat) {
        System.out.println("rc:" + rc + ",path:" + path + ",ctx:" + ctx + ",stat:" + stat);
        boolean exists;
        switch (rc) {
            case KeeperException.Code.Ok:
                exists = true;
                break;
            case KeeperException.Code.NoNode:
                exists = false;
                break;
            case KeeperException.Code.SessionExpired:
            case KeeperException.Code.NoAuth:
                dead = true;
                synchronized (this) {
                    notifyAll();
                }
                return;
            default:
                // Retry errors
                zk.exists(znode, true, this, null);
                return;
        }
        byte b[] = null;
        if (exists) {
            try {
                b = zk.getData(znode, false, null);
                System.out.println("数据:" + new String(b));
            } catch (KeeperException e) {
                // We don't need to worry about recovering now. The watch
                // callbacks will kick off any exception handling
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private final Object o = new Object();

    public void run() {
        try {
            synchronized (o) {
                while (!dead) {
                    o.wait();
                    System.out.println("输出字符串");
                }
            }
        } catch (InterruptedException e) {
        }
    }

    public void printlnStr() {
        synchronized (o) {
            System.out.println("输出A");
        }
    }
}
