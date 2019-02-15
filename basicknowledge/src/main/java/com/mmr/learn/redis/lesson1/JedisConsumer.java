package com.mmr.learn.redis.lesson1;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月15日 14:24
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@Slf4j
public class JedisConsumer {
    private ShardedJedis shardedJedis;
    private String key;

    private JedisConsumer(ShardedJedis shardedJedis,String key) {
        this.shardedJedis=shardedJedis;
        this.key=key;
    }

    public void run() {
        while (true) {
            try{
                if(shardedJedis == null)
                    shardedJedis = JedisPool.getReadJedisObject();
                TimeUnit.SECONDS.sleep(1);
                Collection<Jedis> collection=shardedJedis.getAllShards();
                Iterator<Jedis> jedis = collection.iterator();
                while(jedis.hasNext()){
                    jedis.next().select(8);
                }
                ShardedJedisPipeline pipeline = shardedJedis.pipelined();
                Response<String> incidentChange = pipeline.rpop("incidentChange");
                pipeline.sync();
                String data = incidentChange.get();
                Gson gson = new Gson();
                JedisIncidentChangeEntity entity = gson.fromJson(data, JedisIncidentChangeEntity.class);
                System.out.println(entity);
                //消费完了休息下
                if (null == data){
                    TimeUnit.SECONDS.sleep(5);
                }
            }catch (Throwable e){
                log.warn("the acquire event thread error.",e);
                if (log.isDebugEnabled()){
                    log.debug("stack：", e);
                }
            }
        }
    }
}
