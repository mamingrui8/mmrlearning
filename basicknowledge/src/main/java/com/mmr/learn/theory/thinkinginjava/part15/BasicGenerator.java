package com.mmr.learn.theory.thinkinginjava.part15;

import net.mindview.util.Generator;

/**
 * 一个可以构造任何类型的Generator
 * 该类型必须具备两个特点:
 * 1. 具有public访问权限
 * 2. 具有默认的构造器
 * @author Charles Wesley
 * @date 2019/11/24 17:16
 */
public class BasicGenerator<T> implements Generator<T> {
    private Class<T> type;

    public BasicGenerator(Class<T> type){
        this.type = type;
    }

    @Override
    public T next() {
        try {
            //assume type is a public class:
            return type.newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用create()的好处在于书写方便
     * 允许执行 BasicGenerator.create(MyType.class); 而不必执行: new BasicGenerator<MyType>(MyType.class)
     */
    public static <T> Generator<T> create(Class<T> type){
        return new BasicGenerator(type);
    }
}
