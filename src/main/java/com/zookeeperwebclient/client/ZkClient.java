package com.zookeeperwebclient.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZkClient {

    private static final Logger logger = LoggerFactory.getLogger(ZkClient.class);

    private CuratorFramework zkclient;

    @Autowired
    public ZkClient() throws Exception{
        String zkhost="localhost:2181";//ZK host
        RetryPolicy rp=new ExponentialBackoffRetry(1000, 3);//Retry mechanism
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder().connectString(zkhost)
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000)
                .retryPolicy(rp);
        zkclient = builder.build();
        zkclient.start();// Implemented in the front

        String data = zkclient.getChildren().forPath("/zookeeper").toString();
        logger.debug(data);
    }

    public CuratorFramework getclient(){
        return zkclient;
    }

}
