package com.owl.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by wanghouping on 2018/3/13.
 *
 * @author houping wang
 */
public class BaseTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void zookeeperBase() throws IOException, KeeperException, InterruptedException {
        BasicsApi basicsApi = new BasicsApi();
        basicsApi.run();
    }

    @Test
    public void delete() throws IOException, KeeperException, InterruptedException {
        BasicsApi basicsApi = new BasicsApi();
        basicsApi.delete();
    }

}
