package com.mmr.learn.redis.lesson2;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mamr
 * @date 2020/6/5 9:32 上午
 */
public class CustomLRU<K, V> extends LinkedHashMap<K, V> {
    /**
     * 允许最大缓存大小
     */
    private final Integer MAX_STORAGE;

    public CustomLRU(int cacheSize) {
        // accessOrder=true 意味着LinkedHashMap将按照访问顺序来对数据进行排序，最近访问的放在头部，最不经常访问的放在队尾
        super((int)(Math.ceil(cacheSize/0.75) + 1), 0.75f, true);
        MAX_STORAGE = cacheSize;
    }

    /**
     * 当缓存数量超过限定值时，删除最远最不经常使用的数据
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_STORAGE;
    }
}
