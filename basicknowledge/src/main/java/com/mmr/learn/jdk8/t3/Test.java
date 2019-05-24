package com.mmr.learn.jdk8.t3;

import com.mmr.learn.jdk8.entity.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {


    /**
     * 问题二
     * 调用Collections.sort()方法，通过定制排序比较两个Employee(先按照年龄比，年龄相同再按照姓名比)，
     * 使用Lambda作为参数传递。
     */
    @org.junit.Test
    public void test1(){
        List<Employee> employeeList = new ArrayList<Employee>(){{
            add(Employee.builder().name("Jack").age(25).build());
            add(Employee.builder().name("Blue").age(20).build());
        }};

        Collections.sort(employeeList, (e1, e2)-> {
            if(e1.getAge() == e2.getAge()){
                return e1.getName().compareTo(e2.getName());
            }else{
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        employeeList.forEach(System.out::println);
    }

    /**
     * ①声明函数式接口，接口中声明抽象方法，public String getValue(String str)       
     * ②声明类TestLambda，类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值。
     * ③再将一个字符串的第2个和第4个索引位置进行截取子串。
     */
    public void test2(){
        convertStr("\t\t\t 声明抽象方法  ", str -> str.toUpperCase());
        convertStr("\t\t\t 声明抽象方法  ", str -> str.substring(2, 5));
    }

    private String convertStr(String str, CalculateInterface inter){
        return inter.getValue(str);
    }

    /**
     * ①声明一个带两个泛型的函数式接口，泛型类型为<T,R> T为参数，R为返回值。     
     * ②接口中声明对应的抽象方法。
     * ③在TestLambda类中声明方法，使用接口作为参数，计算两个long型参数的和。     
     * ④再计算两个long型 参数的乘积。
     */
    @org.junit.Test
    public void test3(){
        //计算和
        Long result1 = calculate(1L,2L, (x, y) -> x+y);
        System.out.println(result1);

        //计算乘积
        Long result2 = calculate(1L, 2L, (x, y) -> x * y);
        System.out.println(result2);
    }

    private Long calculate(Long t1 , Long t2, Test3Interface<Long, Long> inter){
        return inter.test(t1, t2);
    }
}
