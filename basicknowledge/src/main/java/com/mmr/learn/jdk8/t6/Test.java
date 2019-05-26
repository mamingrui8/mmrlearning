package com.mmr.learn.jdk8.t6;

import com.google.common.collect.Lists;
import com.mmr.learn.jdk8.entity.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

    /**
     * 流水线式的中间操作 --- 筛选与切片
     */
    @org.junit.Test
    public void test(){
        //中间操作 - filter
        Stream<Employee> stream = employees.stream().filter(e -> {
            System.out.println("Stream Api的中间操作");
            return e.getAge() >= 20;
        }).distinct();

        //终止操作
        stream.forEach(System.out::println);

        //上述测试证实: 单纯的执行中间操作，不会有任何操作被执行。当且仅当执行了终止操作后，中间操作操作会被一次性的全部执行完毕。
    }

    /**
     * 流水线式的中间操作 --- 短路
     */
    @org.junit.Test
    public void test2(){
        employees.stream()
                .filter((x) -> x.getSalary() > 3000)
                .limit(2)
                .forEach(System.out::println);

        //中间操作会一次性加载所有的条件，如本例中，薪资大于4000的人有3位，但由于limit(2)，因此当返回两位员工信息后，stream()就已经短路了。
        //正是有这种短路机制，在某种程度上说，提升了数据迭代的效率。
    }


    /**
     * 映射
     */
    @org.junit.Test
    public void test3(){
        List<String> list1 = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

        //实际上，这种操作就是在处理流中的每一个元素
        list1.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

        System.out.println("================================");

        //再举例: 从员工列表中提取出员工姓名列表
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

        System.out.println("================================");

        //测试flatMap，将list1中的每个字符串拆分成字符，组成新的集合
        list1.stream()
                .flatMap(this::getCharacters) //首先，把方法应用到每一个stream的元素身上，接着，从每一个元素返回的stream中，把子元素抽取出来，组合成一个新的集合。
                .forEach(System.out::print);  //输出: aaabbbcccdddeee

        System.out.println("================================");

        //如果不使用flatMap()，新获得的集合是若干个stream组成的集合
        list1.stream()
                .map(this::getCharacters)
                .forEach(System.out::print); //输出 java.util.stream.ReferencePipeline$Head@71f2a7d5java.util.stream.ReferencePipeline$Head@2cfb4a64j
    }

    private Stream<Character> getCharacters(String str){
        List<Character> list = new ArrayList<>();

        for(Character ch : str.toCharArray()){
            list.add(ch);
        }

        return list.stream();
    }

    List<Employee> employees = Arrays.asList(
            new Employee("Jack1", 18, 8888D),
            new Employee("Jack2", 20, 6666D),
            new Employee("Jack3", 19, 9999D),
            new Employee("Jack4", 25, 3333D),
            new Employee("Jack4", 25, 3333D),
            new Employee("Jack4", 25, 3333D),
            new Employee("Jack5", 25, 7777D),
            new Employee("Jack6", 25, 7777D)
    );

    /**
     * 排序
     * sorted() - 自然排序(Comparable)
     * sorted(Comparator com) - 定制排序(Comparator)
     */
    @org.junit.Test
    public void test4(){
        List<String> list1 = Arrays.asList("ccc", "eee", "aaa", "ddd", "bbb");

        //按照自然排序
        list1.stream()
                .sorted()
                .forEach(System.out::print); //输出: aaabbbcccdddeee
        System.out.println();

        System.out.println("=====================================");

        //按照定制排序

        //先按照薪水降序排序，如果薪水相同，再按照姓名降序升序
        employees.stream()
                .sorted((e1, e2)-> {
                    if(e1.getSalary() == e2.getSalary()){
                        return e1.getName().compareTo(e2.getName());
                    }else{
                        return -e1.getSalary().compareTo(e2.getSalary());
                    }
                })
                .forEach(System.out::println);
    }

    /**
     * 终止操作
     */
}
