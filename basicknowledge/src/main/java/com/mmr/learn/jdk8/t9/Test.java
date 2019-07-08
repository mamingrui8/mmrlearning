package com.mmr.learn.jdk8.t9;

import com.mmr.learn.jdk8.entity.Employee;
import com.mmr.learn.jdk8.entity.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    /**
     * 给定两个集合，分别找出名称相同和名称不同的数据，组成两个新的集合
     */
    @org.junit.Test
    public void test3(){
        List<Employee> list1 = Arrays.asList(
                new Employee("Jack1", 18, 8888D, Status.BUSY),
                new Employee("Jack2", 20, 6666D, Status.RELAX),
                new Employee("Jack3", 20, 6666D, Status.RELAX)
        );

        List<Employee> list2 = Arrays.asList(
                new Employee("Jack2", 25, 9999D, Status.BUSY),
                new Employee("Jack4", 26, 1111D, Status.RELAX),
                new Employee("Jack3", 27, 2222D, Status.RELAX),
                new Employee("Jack5", 27, 2222D, Status.RELAX)
        );


        List<Employee> collect = list2.stream().filter(
                e2 -> list1.stream().anyMatch(
                        e1 -> e2.getName().equals(e1.getName()) || list2.stream().noneMatch(e3 -> e3.getName().equals(e1.getName()))
                )
        ).collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

}
