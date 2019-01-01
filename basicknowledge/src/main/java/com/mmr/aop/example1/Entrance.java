package com.mmr.aop.example1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月16日 16:42
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/config/example2/aop.xml");

        HelloWorld  helloWorldImpl1 = (HelloWorld)context.getBean("helloWorldImpl1");

        helloWorldImpl1.printHelloWorld();
        System.out.println("--------------分割线--------------");
        helloWorldImpl1.doPrint();
    }
}
