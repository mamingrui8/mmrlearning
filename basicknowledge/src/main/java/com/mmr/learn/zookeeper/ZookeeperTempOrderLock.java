package com.mmr.learn.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 使用临时顺序节点来实现分布式锁
 *
 * @author mamr
 * @date 2020/7/30 4:34 下午
 */
@Slf4j
public class ZookeeperTempOrderLock implements Watcher {
    /**
     * Zookeeper客户端
     */
    private ZooKeeper zookeeper;

    /**
     * 锁的唯一标识
     */
    private String lockId;

    /**
     * 与Zookeeper建立会话的信号量
     */
    private CountDownLatch connectedLatch;

    /**
     * 创建分布式锁的过程中，开始和等待请求创建分布式锁的信号标志
     */
    private CountDownLatch creatingLatch;

    /**
     * 分布式锁路径前缀
     */
    private String locksRootPath = "/locks";

    public ZookeeperTempOrderLock(String lockId) {
        this.lockId = lockId;
        try {
            // 会话超时时间
            int sessionTimeout = 3000;
            //
            zookeeper = new ZooKeeper("192.168.0.93:2181", sessionTimeout, this);
            connectedLatch.await();
        } catch (IOException ioe) {
            log.error("与Zookeeper建立连接时出现异常", ioe);
        } catch (InterruptedException ite) {
            log.error("等待与Zookeeper会话建立完成时出现异常", ite);
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedLatch.countDown();
        }

        if (creatingLatch != null) {
            creatingLatch.countDown();
        }
    }

    public void acquireDistributedLock() {

    }

    public boolean tryLock() {
        try {
            String zNode = zookeeper.create(locksRootPath + "/" + lockId,
                    "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException | InterruptedException e) {
            log.error("创建临时顺序节点失败", e);
        }
        return false;
    }
}
