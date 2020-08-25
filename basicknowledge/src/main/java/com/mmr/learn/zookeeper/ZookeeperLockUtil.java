package com.mmr.learn.zookeeper;

import com.mmr.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * Zookeeper分布式锁
 *
 * @author mamr
 * @date 2020/8/4 12:36 下午
 */
@Slf4j
public class ZookeeperLockUtil implements Watcher {
    /**
     * Zookeeper客户端
     */
    private ZooKeeper zookeeper;

    /**
     * 创建Zookeeper Node的信号量
     */
    private CountDownLatch createdZookeeperNodeLatch;

    /**
     * 本次请求获取锁时，创建的临时顺序节点的实际路径
     * 注意: currentNodeLockPath 不一定等于 prefixLockPath + "/" + lockId
     */
    private String currentNodeLockPath;

    /**
     * 分布式锁路径的前缀
     */
    private String prefixLockPath = "/zkLocksWJN";

    /**
     * 分布式锁的唯一ID
     */
    private String lockId;

    /**
     * 待等待的上一个分布式锁创建的临时节点的相对路径
     */
    private String previousNodeLockPath;

    /**
     * 构造函数
     */
    private ZookeeperLockUtil(String lockId) {
        // Zookeeper服务端访问地址 多个地址用逗号隔开
        String zkUrl = "10.1.61.174:2181";
        // 与zookeeper建立一次会话的超时时间 单位: 毫秒
        int sessionTimeout = 60000;
        this.lockId = lockId;
        try {
            zookeeper = new ZooKeeper(zkUrl, sessionTimeout, this);
        } catch (IOException e) {
            log.error("创建Zookeeper客户端时出现异常", e);
        }
    }

    /**
     * 处理监听事件的具体逻辑
     *
     * @param event 监听到的对Zookeeper的相关操作
     */
    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (createdZookeeperNodeLatch != null) {
                createdZookeeperNodeLatch.countDown();
            }
        }
    }

    /**
     * 获取分布式锁
     */
    public void acquireDistributedLock() throws InterruptedException, TimeoutException, KeeperException {
        // zookeeper的节点要一级一级的自顶向下创建
        createRootNode();
        createLockRootNode();

        while (!tryLock()) {
            System.out.println(Thread.currentThread().getName() + " 尝试获取锁失败，等待上一个服务释放锁 " + LocalDateTime.now());
            waitForReleaseLock(previousNodeLockPath, 30000);
        }
    }

    /**
     * 释放分布式锁
     */
    private void releaseLock() {
        try {
            System.out.println(Thread.currentThread().getName() + " 释放分布式锁, currentNodeLockPath->" + currentNodeLockPath);
            zookeeper.delete(currentNodeLockPath, -1);
            currentNodeLockPath = null;
            zookeeper.close();
        } catch (InterruptedException | KeeperException e) {
            log.error("释放分布式锁失败,路径为: " + currentNodeLockPath, e);
        }
    }

    /**
     * 等待其它服务释放上一把锁
     *
     * @param nodePath                   等待释放锁对应临时节点的相对路径
     * @param waitForPreviousLockRelease 等待释放锁的时间 不能超过一次Zookeeper会话的时间
     */
    private void waitForReleaseLock(String nodePath, long waitForPreviousLockRelease) throws KeeperException, InterruptedException, TimeoutException {
        Stat stat = zookeeper.exists(prefixLockPath + "/" + lockId + "/" + nodePath, true);
        if (stat != null) {
            this.createdZookeeperNodeLatch = new CountDownLatch(1);
            boolean await = this.createdZookeeperNodeLatch.await(waitForPreviousLockRelease, TimeUnit.MILLISECONDS);
            this.createdZookeeperNodeLatch = null;
            if(!await) {
                throw new TimeoutException("等待其它服务释放锁动作超时");
            }
        }
    }

    /**
     * 尝试获取分布式锁
     */
    private boolean tryLock() {
        // 创建临时顺序节点
        // OPEN_ACL_UNSAFE指的是对节点操作时，无需考虑任何ACL权限控制
        try {
            if(StringUtils.isEmpty(currentNodeLockPath)) {
                currentNodeLockPath = zookeeper.create(prefixLockPath + "/" + lockId + "/" + UUIDUtils.randomUUID(), "".getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            }

            // 查看刚创建的节点有没有兄弟节点，是不是兄弟节点中最小的那个节点
            List<String> brotherNodes = zookeeper.getChildren(prefixLockPath + "/" + lockId, false);

            Collections.sort(brotherNodes);
            if (currentNodeLockPath!=null && !CollectionUtils.isEmpty(brotherNodes)
                    && currentNodeLockPath.split(prefixLockPath + "/" + lockId + "/")[1].equals(brotherNodes.get(0))) {
                // 当前节点就是最小节点，意味着成功获取到锁
                return true;
            }
            // 如果不是最小节点,则找到比自己小1的节点
            // 上一个分布式锁对应节点的序号
            int previousLockNodeIndex = -1;
            for (int i = 0; i < brotherNodes.size(); i++) {
                if (currentNodeLockPath.equals(prefixLockPath + "/" + lockId + "/" + brotherNodes.get(i))) {
                    previousLockNodeIndex = i - 1;
                    break;
                }
            }
            previousNodeLockPath = brotherNodes.get(previousLockNodeIndex);
        } catch (KeeperException | InterruptedException e) {
            log.error("创建临时顺序节点出现异常", e);
        }
        return false;
    }

    private void createRootNode() {
        try {
            Stat rootStat = zookeeper.exists(prefixLockPath, true);
            if (rootStat == null) {
                zookeeper.create(prefixLockPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createLockRootNode() {
        try {
            Stat lockRootStat = zookeeper.exists(prefixLockPath + "/" + lockId, false);
            if (lockRootStat == null) {
                zookeeper.create(prefixLockPath + "/" + lockId, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                System.out.println("t1 run()...");
                ZookeeperLockUtil zookeeperLockUtil = new ZookeeperLockUtil("business1");

                try {
                    System.out.println("t1 尝试获取分布式锁");
                    zookeeperLockUtil.acquireDistributedLock();
                    System.out.println("t1 模拟执行业务代码,等待15秒...");
                    TimeUnit.SECONDS.sleep(15);
                    System.out.println("t1 释放分布式锁");
                    zookeeperLockUtil.releaseLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("=========");
        Thread t2 = new Thread("t2"){
            @Override
            public void run() {
                System.out.println("t2 run()...");
                ZookeeperLockUtil zookeeperLockUtil = new ZookeeperLockUtil("business1");
                try {
                    System.out.println("t2 尝试获取分布式锁");
                    zookeeperLockUtil.acquireDistributedLock();
                    System.out.println("t2 模拟执行业务代码");
                    System.out.println("t2 释放分布式锁");
                    zookeeperLockUtil.releaseLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t2.start();
    }
}
