package com.mmr.learn.jdk8.t7;

import com.mmr.learn.jdk8.entity.Employee;
import com.mmr.learn.jdk8.entity.Status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 终止操作
 */
public class Test {

    List<Employee> employees = Arrays.asList(
            new Employee("Jack1", 18, 8888D, Status.BUSY),
            new Employee("Jack2", 20, 6666D, Status.RELAX),
            new Employee("Jack3", 19, 9999D, Status.VOCATION),
            new Employee("Jack4", 25, 3333D, Status.RELAX),
            new Employee("Jack4", 25, 3333D, Status.VOCATION),
            new Employee("Jack4", 25, 3333D, Status.BUSY),
            new Employee("Jack5", 25, 7777D, Status.BUSY),
            new Employee("Jack6", 25, 7777D, Status.VOCATION)
    );

    /**
     * 查找与匹配
     * 1. allMatch 检查是否匹配所有元素
     * 2. anyMatch 检查是否至少匹配一个元素
     * 3. noneMatch 检查是否没有匹配所有元素
     * 4. findFirst 返回第一个元素
     * 5. findAny 返回当前流中的任意元素
     * 6. count 返回流中元素的总个数
     * 7. max  返回流中的最大值
     * 8. min 返回流中的最小值
     */
    @org.junit.Test
    public void test1(){
        boolean b1 = employees.stream()
                .allMatch(e -> e.getStatus().equals(Status.BUSY));
        System.out.println("所有员工是否都处于BUSY状态: " + b1); //false

        boolean b2 = employees.stream()
                .anyMatch(e -> e.getStatus().equals(Status.BUSY));
        System.out.println("是否有员工的状态为BUSY: " + b2);  //true

        boolean b3 = employees.stream()
                .noneMatch(e -> e.getStatus().equals(Status.EXCITING));
        System.out.println("是否没有任何一位员工处于EXCITING状态:" + b3); // true

        Optional<Employee> op1 = employees.stream()
                .sorted((e1, e2) -> {
                    return -e1.getSalary().compareTo(e2.getSalary());
                })
                .findFirst();
        System.out.println("薪酬待遇最高的员工信息: " + op1.get());

        Optional<Employee> op2 = employees.stream()
                .sorted((e1, e2) -> {
                    return -e1.getSalary().compareTo(e2.getSalary());
                })
                .findAny();
        System.out.println("任意一名员工的信息: " + op2.get());

        Long sum = employees.stream().count();
        System.out.println("员工数量: " + sum);

        Optional<Employee> op3 = employees.stream()
                .max((e1, e2) -> e1.getSalary().compareTo(e2.getSalary()));
        System.out.println("薪酬待遇最高的员工信息: " + op3.get());

        Optional<Double> op4 = employees.stream()
                .map(Employee::getSalary)
                .min(Double::compare);
        System.out.println("最低工资: " + op4.get());
    }

}
