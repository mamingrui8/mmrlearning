package com.mmr.learn.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
    private final String lockId;

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
    private final String locksRootPath = "/locks";

    /**
     * 排在前面的节点的路径
     */
    private String waitNodeLockPath;

    /**
     * 本次创建的节点的路径
     */
    private String currentNodeLockPath;

    public ZookeeperTempOrderLock(String lockId) {
        this.lockId = lockId;
        try {
            // 会话超时时间
            int sessionTimeout = 30000;
            //
            zookeeper = new ZooKeeper("192.168.0.93:2181", sessionTimeout, this);
            connectedLatch.await();
        } catch (IOException ioe) {
            log.error("与Zookeeper建立连接时出现异常", ioe);
        } catch (InterruptedException ite) {
            log.error("等待与Zookeeper会话建立完成时出现异常", ite);
        }
    }

    /**
     * 监听器的具体业务逻辑
     *
     * @param event 监听到的事件
     */
    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedLatch.countDown();
        }

        if (creatingLatch != null) {
            creatingLatch.countDown();
        }
    }

    /**
     * 获取锁
     */
    public void acquireDistributedLock() {
        try {
            while(!tryLock()) {
                // 等待前一项服务释放锁的等待时间 不能超过一次Zookeeper会话的时间
                long waitForPreviousLockRelease = 30000;
                waitForLock(waitNodeLockPath, waitForPreviousLockRelease);
            }
        } catch (InterruptedException | KeeperException e) {
            log.error("等待上锁的过程中出现异常", e);
        }
    }

    public boolean tryLock() {
        try {
            // 创建顺序临时节点
            currentNodeLockPath = zookeeper.create(locksRootPath + "/" + lockId,
                    "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            // 查看刚刚创建的节点是不是最小节点
            // 比如针对于这个同名节点，之前有其它服务曾申请创建过，因此Zookeeper中临时顺序节点形如:
            // /locks/10000000000, /locks/10000000001, /locks/10000000002
            List<String> nodePaths = zookeeper.getChildren(locksRootPath, false);
            Collections.sort(nodePaths);
            if(currentNodeLockPath.equals(locksRootPath + "/" + nodePaths.get(0))) {
                // 如果是最小节点，则代表获取到锁
                return true;
            }
            // 如果不是最小节点，则找到比自己小1的节点 (紧挨着自己)
            int previousLockNodeIndex = -1;
            for (int i = 0; i < nodePaths.size(); i++) {
                if(currentNodeLockPath.equals(locksRootPath + "/" + nodePaths.get(i))) {
                    previousLockNodeIndex = i-1;
                    break;
                }
            }
            this.waitNodeLockPath = nodePaths.get(previousLockNodeIndex);

        } catch (KeeperException | InterruptedException e) {
            log.error("创建临时顺序节点失败", e);
        }
        return false;
    }

    /**
     * 等待其他服务释放锁
     * 实际上就是在等待前一个临时节点被删除
     *
     * @param nodePath 希望被删除的节点的相对路径
     * @param waitTime 等待时长 单位:毫秒
     */
    private boolean waitForLock(String nodePath, long waitTime) throws KeeperException, InterruptedException {
        // 查看等待的节点是否存在 并且对这个节点注册一个监听器
        Stat stat = zookeeper.exists(locksRootPath + "/" + nodePath, true);

        if (stat != null) {
            // 如果存在，那就没办法了，只能等呗
            this.creatingLatch = new CountDownLatch(1);
            this.creatingLatch.await(waitTime, TimeUnit.MILLISECONDS);
            this.creatingLatch = null;
        }
        return true;
    }

    /**
     * 释放锁
     * 实际上就是删除当前创建的临时节点
     */
    public void releaseLock() {
        log.info("准备删除的节点路径: " + currentNodeLockPath);
        try {
            zookeeper.delete(currentNodeLockPath, -1);
            currentNodeLockPath = null;
            zookeeper.close();
        } catch (Exception e) {
            log.error("删除节点失败", e);
        }
    }
}
