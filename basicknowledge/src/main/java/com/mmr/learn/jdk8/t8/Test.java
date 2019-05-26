package com.mmr.learn.jdk8.t8;

import com.mmr.learn.jdk8.entity.Employee;
import com.mmr.learn.jdk8.entity.Status;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 归约与收集
 */
public class Test {

    /**
     * 归约
     */
    @org.junit.Test
    public void test(){
        List<Integer> lists = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //求出集合中所有数字的和
        Integer sum = lists.stream()
                .reduce(0, (Integer::sum)); //identity是一个起始值
         System.out.println(sum);

        //之所以这里得到的结果时Optional<Integer>，是因为此次归约中没有给定初始值，
        //这样就有可能导致最终的结果为null，因此jdk1.8使用容器类Optional来装载结果
        Optional<Integer> reduce = lists.stream()
                .reduce(Integer::sum);

    }

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
     * 收集
     */
    @org.junit.Test
    public void test2(){
        List<String> stringList = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        stringList.forEach(System.out::println);

        //将收集的结果放入Set中
        Set<String> collect = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());

        //将收集的结果放入任意的Collection集合中
        HashSet<String> collect1 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));  //Supplier接口，自己实现即可

        //总数
        Long sum = employees.stream()
                .collect(Collectors.counting());

        //平均值
        Double avgSalary = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));

        //总和
        Double sumSalary = employees.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));

        //最大值
        Optional<Employee> op1 = employees.stream()
                .collect(Collectors.maxBy((e1, e2) -> e1.getSalary().compareTo(e2.getSalary())));
    }

    /**
     * 分组
     */
    @org.junit.Test
    public void test3(){
        //以状态为员工进行分组
        Map<Status, List<Employee>> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));

        System.out.println(collect);

        System.out.println("=================================");

        //多级分组
        //先按状态分组，再按年龄段分组
        //注意: collect(Function, Collect) 可以无限嵌套
        Map<Status, Map<String, List<Employee>>> map = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if(((Employee)(e)).getAge() <= 30){
                        return "青年";
                    }else if(((Employee)(e)).getAge() <= 50){
                        return "中年";
                    }else{
                        return "老年";
                    }
                })));
        System.out.println(map);
    }

    /**
     *  分区
     *  满足条件和不满足条件，分成“false”和“true”两个区返回
     */
    @org.junit.Test
    public void test4(){
        //按照薪资待遇是否大于7000进行分区
        Map<Boolean, List<Employee>> map = employees.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() > 7000));
        System.out.println(map);
    }

    /**
     * 总结(概要)
     */
    @org.junit.Test
    public void test5(){
        DoubleSummaryStatistics dss = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        System.out.println(dss.getMax());
        System.out.println(dss.getAverage());
        System.out.println(dss.getCount());
        System.out.println(dss.getMin());
        System.out.println(dss.getSum());
    }

    /**
     * 连接
     */
    @org.junit.Test
    public void test6(){
        String joinedStr1 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(","));
        System.out.println(joinedStr1); // Jack1,Jack2,Jack3,Jack4,Jack4,Jack4,Jack5,Jack6

        //如果想在首部或尾部接上特定字符串，可以按照以下写法
        String joinedStr2 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",","我爱你","祖国")); //
        System.out.println(joinedStr2); //我爱你Jack1,Jack2,Jack3,Jack4,Jack4,Jack4,Jack5,Jack6祖国
    }
}
