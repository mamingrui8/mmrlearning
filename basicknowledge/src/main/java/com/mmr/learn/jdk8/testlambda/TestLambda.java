package com.mmr.learn.jdk8.testlambda;

import com.mmr.learn.jdk8.entity.Employee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class TestLambda {

    /**
     * 原来的匿名内部类
     */
    public void test1(){
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        TreeSet<Integer> ts = new TreeSet<>(comparator);
    }

    /**
     * Lambda表达式加工后
     */
    public void test2(){
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> ts = new TreeSet<>(comparator);
    }







    /**
     * 获取当前员工中年龄大于20岁的员工信息 (原先做法)
     */
    public void test3_1(){
        //获取所有的员工信息列表
        List<Employee> employeeList = new ArrayList<>();
        //年龄大于20岁的员工信息列表
        List<Employee> newEmployeeList = new ArrayList<>();

        employeeList.forEach(employee -> {
            if(employee.getAge() > 20){
                newEmployeeList.add(employee);
            }
        });
    }

    /**
     * 获取当前员工中薪资待遇大于5000的员工信息
     */
    public void test3_2(){
        //获取所有的员工信息列表
        List<Employee> employeeList = new ArrayList<>();
        //年龄大于20岁的员工信息列表
        List<Employee> newEmployeeList = new ArrayList<>();

        employeeList.forEach(employee -> {
            if(employee.getSalary() > 5000){
                newEmployeeList.add(employee);
            }
        });
    }

    /**
     * 优化1
     */
    public List<Employee> test4(List<Employee> employeeList, Predicate<Employee> predicate){
        //年龄大于20岁的员工信息列表
        List<Employee> newEmployeeList = new ArrayList<>();
        employeeList.forEach(employee -> {
            if(predicate.test(employee)){
                newEmployeeList.add(employee);
            }
        });

        return newEmployeeList;
    }

    /**
     * 优化1: 策略模式
     */
    public void test5(){
        //获取所有的员工信息列表
        List<Employee> employeeList = new ArrayList<>();
        //获取年龄大于20岁的员工信息列表
        List<Employee> newEmployeeList1 = test4(employeeList, new CompareByAgePredicate());
        //获取薪资待遇大于5000的员工信息列表
        List<Employee> newEmployeeList2 = test4(employeeList, new CompareBySalaryPredicate());
    }

    /**
     * 优化2: 匿名内部类
     */
    public void test6(){
        //获取所有的员工信息列表
        List<Employee> employeeList = new ArrayList<>();
        //获取年龄大于20岁的员工信息列表
        List<Employee> newEmployeeList1 = test4(employeeList, new Predicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getAge()>20;
            }
        });

        //获取薪水大于5000的员工信息列表
        List<Employee> newEmployeeList2 = test4(employeeList, new Predicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() > 5000;
            }
        });
    }

    /**
     * 优化3: 使用Lambda表达式
     */
    public void test7(){
        //获取所有的员工信息列表
        List<Employee> employeeList = new ArrayList<>();
        //获取年龄大于20岁的员工信息列表
        List<Employee> newEmployeeList1 = test4(employeeList, (employee) -> employee.getAge()>20);
        newEmployeeList1.forEach(System.out::println);

        //获取薪水大于5000的员工信息列表
        List<Employee> newEmployeeList2 = test4(employeeList, (employee -> employee.getSalary()>5000));
        newEmployeeList2.forEach(System.out::println);
    }

    /**
     * 优化4:
     */
    public void test8(){
        //获取所有的员工信息列表
        List<Employee> employeeList = new ArrayList<>();

        employeeList.stream()
                .filter((e) -> e.getSalary() >=5000)
                .limit(2)
                .forEach(System.out::println);
    }
}

interface Predicate<Employee>{
    boolean test(Employee employee);
}

/**
 * 按照年龄过滤
 */
class CompareByAgePredicate implements Predicate<Employee>{

    @Override
    public boolean test(Employee employee) {
        return employee.getAge() > 20;
    }
}

/**
 * 按照薪资待遇过滤
 */
class CompareBySalaryPredicate implements Predicate<Employee>{

    @Override
    public boolean test(Employee employee) {
        return employee.getSalary() > 5000;
    }
}

