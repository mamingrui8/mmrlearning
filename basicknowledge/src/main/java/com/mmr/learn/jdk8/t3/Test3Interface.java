package com.mmr.learn.jdk8.t3;

@FunctionalInterface
public interface Test3Interface<T, R> {
    R test(T t1, T t2);
}
