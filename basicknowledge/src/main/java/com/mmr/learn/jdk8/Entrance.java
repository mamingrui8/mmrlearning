package com.mmr.learn.jdk8;

import com.mmr.learn.jdk8.entity.Employee;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Entrance {
    /*
     *  t3 Lambda练习
     *  t4 四大内置核心函数式接口
     *  t5 方法引用与构造器引用
     *  t6 创建Stream
     *  t7 查找与匹配 (终止操作)
     *  t8 归约与收集
     *  t9 Stream API练习
     *  t10 并行流与顺序流
     *  t11 Optional容器类
     *  t12 接口中的默认方法和静态方法
     *  t13 新时间与日期
     */

    public static void main(String[] args) {

        System.out.println(int[].class);   //输出class [I

        System.out.println(char[].class); //输出class [C

        System.out.println(int.class); //输出int

        System.out.println(Employee.class); //class com.mmr.learn.jdk8.entity.Employee   (这是我自己定义的类型)

        System.out.println(Integer.class);

        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.systemDefault());

        System.out.println(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));

        test1();
    }

    public static void test1(){
        Integer[][] ss = {{1,2,3}, {2,2,3}, {3,3,4}};

        System.out.println(ss[1][1]);
    }
}
