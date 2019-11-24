package com.mmr.learn.theory.thinkinginjava.part15.common;

@FunctionalInterface
public interface Generator<T> {
    T next();
}