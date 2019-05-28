package com.mmr.learn.jdk8.t12;

/**
 * 接口中含有被default修饰的非抽象方法
 */
public interface MyFun1 {
    default String getName(){
        return "MyFun1";
    };

    static Double getTotal(){ //访问修饰符可以省略，默认public
        return 88.8;
    }
}
