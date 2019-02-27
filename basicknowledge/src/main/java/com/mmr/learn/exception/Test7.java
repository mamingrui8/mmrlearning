package com.mmr.learn.exception;

import org.slf4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-26
 * Time: 17:49
 */
public class Test7 {
    //public static final Logger LOG = LoggerFactory.getLogger("com.mmr.learn.exception.Test7"); //具有给定名称的"日志记录器",我们人为规定此处使用包名+类名

    public static void main(String[] args){
        for(int i = 0; i< 10 ;i++){
            test1();
        }

    }

    /*
        参考文档:
        1. https://blog.csdn.net/foreverling/article/details/51385128
        2. https://www.jb51.net/article/71692.htm  (详细的slf4j配置文件说明)
        slf4j是一个接口，他允许用户以自己的喜好，在工程中通过slf4j接入不同的日志系统。更直观一点，slf4j是个数据线，一端嵌入程序，另一端链接日志系统，从而实现将程序中的信息导入到日志系统并记录。
        他不负责具体的日志实现，只在编译时负责寻找合适的日志系统进行绑定。
        具体的操作步骤如下:
        1. 首先，系统包含slf4j-api作为日志接入的接口
        2. 在编译时，LoggerFactory的performInitialization()方法将会 进行初始化工作，首当其冲的会调用bind()。该方法用于寻找具体的日志实现类，并与之绑定。
           那么到底是如何搜索到具体的日志实现类的呢？？？
        3. 通过slf4j的StaticLoggerBinder.getSingleton()语句调用了slf4j-log12链接slf4j-api和log4j中间的适配器。它实现的是slf4j-api中StaticLoggerBinder接口，从而使得在编译时和slf4j-api绑定的是slf4j-log12的getSingleton()方法

        关于slf4j的配置文件，请参考logback.xml


        logback默认配置的步骤:
            1. 尝试在classpath下查找文件 logback-test.xml
            2. 如果文件不存在，则查找文件logback.xml
            3. 如果logback-text.xml和logback.xml都不存在，logback会BasicConfiguration自动对自己进行配置，这会导致记录输出到控制台。
     */


    /**
     * 最基本的日志记录
     */
    public static void test1(){
        class student{
            String name;
            int age;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public student(String  name, int age){
                this.name = name;
                this.age = age;

            }
            public String toString(){
                return "student name: " + this.name + ";age: " + this.age;
            }
        }

        student stu = new student("马明瑞", 24);
        //普通的输入的message
//        LOG.info("测试: " + stu);
//        //使用占位符
//        LOG.debug("占位符。姓名: {}, 年龄: {}", stu.getName(), stu.getAge());
//        LOG.warn("你好");
    }


}
