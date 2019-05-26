package com.mmr.learn.jdk8.t5;

import com.mmr.learn.jdk8.entity.Employee;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 方法引用和构造器引用
 *
 * 方法引用主要有以下三种表现形式:
 * 1. 对象::实例方法名
 * 2. 类::静态方法名
 * 3. 类::实例方法名
 *
 *
 * 构造器引用
 * ClassName::new
 */
public class Test {

    /**
     * 数组引用
     */
    @org.junit.Test
    public void test6(){
        //获取指定长度的空数组

        //原来的方法
        Function<Integer, String[]> func1 = (x) -> new String[x];
        System.out.println(func1.apply(10).length);

        //使用方法引用
        Function<Integer, String[]> func2 = String[]::new;
        System.out.println(func2.apply(20).length);
    }

    /**
     * 构造器引用
     */
    public void test5(){
        //创建对象

        //原来的方法
        Supplier<Employee> supplier1 = () -> new Employee();

        //使用方法引用
        Supplier<Employee> supplier2 = Employee::new;

        /*
            注意，使用构造器引用时，最终调用哪个构造函数，与函数式接口的方法参数列表相关。
         */
        Function<Integer, Employee> function = Employee::new;
    }


    /**
     * 对象::实例方法名
     */
    public void test1(){
        //之前的写法
        PrintStream ps1 = System.out;
        Consumer<String> consumer1 = x -> ps1.println(x);

        //使用方法引用
        PrintStream ps2 = System.out;
        Consumer<String> consumer2 = ps2::println;

        //更进一步
        Consumer<String> consumer3 = System.out::println;

        /*
            但使用方法引用有一个前提: 待实现的接口的参数列表和返回值必须与方法引用的参数类表和返回值保持一致
            比如此处:
            Consumer中的抽象方法  void accept(T t);
            PrintStream中已经实现的方法引用: void println(String x);
         */
    }

    @org.junit.Test
    public void test2(){
        Employee employee = new Employee("Jack", 25, 22000D);

        //之前的写法
        Supplier<String> supplier1 = () -> employee.getName();
        System.out.println(supplier1.get());

        //使用方法引用
        Supplier<Integer> supplier2 = employee::getAge; //由于参数列表一致，因此连( )括号都不用写了
        System.out.println(supplier2.get());
    }

    /**
     * 类::静态方法
     */
    public void test3(){
        //比较两个数的大小

        //之前的写法
        Comparator<Integer> comparator1 = (x, y) -> Integer.compare(x, y);

        //使用方法引用
        Comparator<Integer> comparator2 = Integer::compare;
    }

    /**
     * 类::实例方法
     */
    public void test4(){
        //比较两个字符串是否相同

        //之前的写法
        BiPredicate<String, String> biPredicate1 = (x, y) -> x.equals(y); //注意:String equals( )方法是一个实例方法

        //使用方法引用
        BiPredicate<String, String> biPredicate2 = String::equals;

        /*
            使用类::实例方法 这种方法引用有其前提条件:
            Lambda参数列表的第一个参数必须是实例方法的调用者，第二个参数必须是实例方法的参数。
         */
    }
}
