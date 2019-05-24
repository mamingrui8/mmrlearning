package com.mmr.learn.jdk8.t6;

import com.mmr.learn.jdk8.entity.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stream API
 * 一.创建流
 * 二.流水线式的中间操作
 * 三.终止操作
 */
public class Test {
    /**
     * 创建Stream
     */
    @org.junit.Test
    public void test1(){
        //1. 通过Collection系列集合提供的stream()或parallelStream()将集合转换成集合流
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2. 通过Arrays的静态方法stream()将数组转换成数组流
        Employee[] employee = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(employee);

        //3. 通过Stream中的静态方法of()
        Stream<String> stream3 = Stream.of("a", "b", "c");

        //4. 创建无限流
        //4.1 迭代
        Stream<Integer> stream4 = Stream.iterate(0, x->x+2); //给出一个seed==0，根据一元表达式进行迭代产生无限流
        //stream4.forEach(System.out::println);

        //4.2 生成
        Stream<Double> stream5 = Stream.generate(Math::random);
        stream5.forEach(System.out::println);
    }

    List<Employee> employees = Arrays.asList(
            new Employee("Jack1", 18, 8888),
            new Employee("Jack2", 20, 6666),
            new Employee("Jack3", 19, 9999),
            new Employee("Jack4", 25, 3333)
    );

    /**
     * 流水线式的中间操作 --- 筛选与切片
     */
    @org.junit.Test
    public void test(){
        //中间操作 - filter
        Stream<Employee> stream = employees.stream().filter(e -> {
            System.out.println("Stream Api的中间操作");
            return e.getAge() >= 20;
        });

        //终止操作
        stream.forEach(System.out::println);

        //上述测试证实: 单纯的执行中间操作，不会有任何操作被执行。当且仅当执行了终止操作后，中间操作操作会被一次性的全部执行完毕。
    }

}
