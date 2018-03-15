# zookeeperD
Demo of Zookeeper

* 为什么线程wait()，需要在Synchronized中才能notify()?
synchronized (this) { 
  notifyAll();
}
