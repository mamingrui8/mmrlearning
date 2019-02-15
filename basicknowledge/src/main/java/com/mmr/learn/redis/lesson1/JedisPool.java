package com.mmr.learn.redis.lesson1;

import redis.clients.jedis.*;

import java.util.LinkedList;
import java.util.List;

public class JedisPool {
    private static ShardedJedisPool pool;

    //静态代码初始化池配置
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        // 集群
        JedisShardInfo jedisShardInfo1 = new JedisShardInfo("192.168.0.61", 6379);
        jedisShardInfo1.setPassword("Root_123");
        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
        list.add(jedisShardInfo1);
        pool = new ShardedJedisPool(config, list);

    }

    /**获得jedis对象*/
    public static ShardedJedis getReadJedisObject(){
        return pool.getResource();
    }

    /**归还jedis对象*/
    public static void returnJedisOjbect(Jedis jedis){
        if (jedis != null) {
            jedis.close();
        }
    }
}
