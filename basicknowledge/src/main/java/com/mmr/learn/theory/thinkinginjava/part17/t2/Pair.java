package com.mmr.learn.theory.thinkinginjava.part17.t2;

/**
 * Key和value都是public final的，这是为了使Pair成为只读数据的传递对象
 * @param <K>
 * @param <V>
 */
public class Pair<K,V> {
    public final K key;
    public final V value;
    public Pair(K k, V v){
        key = k;
        value = v;
    }
}
