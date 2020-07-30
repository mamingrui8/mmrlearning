package com.mmr.learn.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 使用临时节点来实现Zookeeper分布式锁
 *
 * @author mamr
 * @date 2020/7/30 11:05 上午
 */
@Slf4j
public class ZookeeperTempLock {
    /**
     * 与Zookeeper成功建立连接的信号标志
     */
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);

    /**
     * 创建分布式锁的过程中，开始和等待请求创建分布式锁的信号标志
     */
    private CountDownLatch creatingSemaphore;

    /**
     * Zookeeper客户端
     */
    private ZooKeeper zookeeper;

    public static void main(String[] args) throws InterruptedException{
        long lockId = 20200730;

        new Thread(() ->{
            ZookeeperTempLock zookeeperLock = new ZookeeperTempLock();
            System.out.println("ThreadId1=" + Thread.currentThread().getId());
            System.out.println("ThreadId=" + Thread.currentThread().getId() + "获取到分布式锁: " + zookeeperLock.acquireDistributeLock(lockId));
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.error("ThreadId=" + Thread.currentThread().getId() + "暂停时出现异常", e);
            }
            zookeeperLock.releaseDistributedLock(lockId);
        }).start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            ZookeeperTempLock zookeeperLock = new ZookeeperTempLock();
            System.out.println("ThreadId2=" + Thread.currentThread().getId());
            System.out.println("ThreadId=" + Thread.currentThread().getId() + "获取到分布式锁: " + zookeeperLock.acquireDistributeLock(lockId));
        }).start();
    }

    public ZookeeperTempLock() {
        try {
            this.zookeeper = new ZooKeeper("192.168.0.93:2181", 5000, new ZookeeperWatcher());
            try {
                connectedSemaphore.await();
            } catch (InterruptedException ite) {
                log.error("等待Zookeeper成功建立连接的过程中，线程抛出异常", ite);
            }
            log.info("与Zookeeper成功建立连接");
        } catch (Exception e) {
            log.error("与Zookeeper建立连接时出现异常", e);
        }
    }

    /**
     * 分布式锁的过期时间 单位:毫秒
     */
    private static final Long DISTRIBUTED_KEY_OVERDUE_TIME = 30000L;

    /**
     * 获取分布式锁
     *
     * @param lockId 锁的唯一标识
     */
    public boolean acquireDistributeLock(Long lockId) {
        String path = "/product-lock-" + lockId;

        try {
            // 尝试创建临时节点znode
            // create(final String path, byte data[], List<ACL> acl,CreateMode createMod)
            // path: 从根节点"/"到当前节点的全路径
            // data: 当前节点存储的数据 (由于这里只是借助临时节点的创建来实现分布式锁，因此无需存储数据)
            // acl:  Access Control list 访问控制列表 主要涵盖权限模式(Scheme)、授权对象(ID)、授予的权限(Permission)这三个方面
            //       OPEN_ACL_UNSAFE 完全开放的访问控制 对当前节点进行操作时，无需考虑ACL权限控制
            // createMode: 节点创建的模式
            //             EPHEMERAL(临时节点) 当创建节点的客户端与zk断开连接后，临时节点将被删除
            //             EPHEMERAL_SEQUENTIAL(临时顺序节点)
            //             PERSISTENT(持久节点)
            //             PERSISTENT_SEQUENTIAL(持久顺序节点)
            zookeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            log.info("ThreadId=" + Thread.currentThread().getId() + "创建临时节点成功");
            return true;
        } catch (Exception e) {
            // 若临时节点已存在，则会抛出异常: NodeExistsException
            while (true) {
                // 相当于给znode注册了一个监听器，查看监听器是否存在
                try {
                    Stat stat = zookeeper.exists(path, true);
                    if (stat != null) {
                        this.creatingSemaphore = new CountDownLatch(1);
                        this.creatingSemaphore.await(DISTRIBUTED_KEY_OVERDUE_TIME, TimeUnit.MILLISECONDS);
                        this.creatingSemaphore = null;
                    }
                    zookeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                    return true;
                } catch (Exception ex) {
                    log.error("ThreadId=" + Thread.currentThread().getId() + ",查看临时节点时出现异常", ex);
                }
            }
        }
    }

    /**
     * 释放分布式锁
     *
     * @param lockId 锁的唯一标识
     */
    public void releaseDistributedLock(Long lockId) {
        String path = "/product-lock-" + lockId;
        try {
            // 第二个参数version是数据版本 每次znode内数据发生变化，都会使version自增，但由于分布式锁创建的临时znode没有存数据，因此version=-1
            zookeeper.delete(path, -1);
            log.info("成功释放分布式锁, lockId=" + lockId + ", ThreadId=" + Thread.currentThread().getId());
        } catch (Exception e) {
            log.error("释放分布式锁失败，lockId=" + lockId, e);
        }
    }

    /**
     * 建立Zookeeper Session的监听器
     * 能监听很多事件，比如ZK客户端与服务端成功连接并创建Session，比如分布式锁被释放
     */
    private class ZookeeperWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            log.info("接收到事件: " + event.getState() + ", ThreadId=" + Thread.currentThread().getId());

            if (Event.KeeperState.SyncConnected == event.getState()) {
                connectedSemaphore.countDown();
            }

            if (creatingSemaphore != null) {
                creatingSemaphore.countDown();
            }
        }
    }
}
