package com.mmr.learn.jdk8.t9;

import com.mmr.learn.jdk8.entity.Employee;
import com.mmr.learn.jdk8.entity.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream Api练习
 */
public class Test {
    List<Employee> employees = Arrays.asList(
            new Employee("Jack1", 18, 8888D, Status.BUSY),
            new Employee("Jack2", 20, 6666D, Status.RELAX),
            new Employee("Jack3", 19, 9999D, Status.VOCATION),
            new Employee("Jack4", 50, 3333D, Status.RELAX),
            new Employee("Jack4", 40, 3333D, Status.VOCATION),
            new Employee("Jack4", 55, 3333D, Status.BUSY),
            new Employee("Jack5", 30, 7777D, Status.BUSY),
            new Employee("Jack6", 25, 7777D, Status.VOCATION)
    );

    /**
     *  给定一个数字集合，如何返回一个由每个数的平台构成的列表？
     *  要求使用map-reduce
     */
    @org.junit.Test
    public void test1(){
        //目标集合
        List<Integer> targetList = Arrays.asList(0,1,2,3,4,5,6);

        List<Double> finalList = targetList.stream()
                .map(e -> Math.pow(e, 2))
                .collect(Collectors.toList());

        finalList.forEach(System.out::println);
    }

    /**
     * 怎样用map-reduce方法数一数流中共有多少个Employee呢？
     */
    @org.junit.Test
    public void test2(){
        Integer num = employees.stream()
                .map((e)->1)
                .reduce(0, (Integer::sum));
        System.out.println("流中共有: " + num + "个元素");
    }

}
