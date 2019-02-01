package com.mmr.learn.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-26
 * Time: 18:27
 * 学习: 调试技巧
 */
public class Test8 {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.mmr.learn.exception.Test8");

    public static void main(String[] args){
//        Random generator = new Random();
//        System.out.println(generator.nextDouble()); //其实就是  返回一个从随机数生成器的序列中均匀分布的0.0和1.0之间的double值
//                                                     //并且听闻generator.nextDouble()其实就是Math.Random()方法的实现。
//
        Test8 test8 = new Test8();
        test8.method6();


    }

    /*
        至关重要的调试技巧！！！
     */

    /**
     * 方法1: 打印或记录日志变量的值
     */
    public void method1(){
        int x = 123;
        System.out.println("现在开始记录日志啦，x=" + x);
        //或者
        LOGGER.info("现在开始记录日志啦，x=" + x);
        //反正充分的利用对象的toString()方法
    }

    /**
     * 方法2: 每个类都放一个main()方法...这样相当于为每个类都搞了一个单元测试。
     * 另外，可以为每个类都保留一个main方法，然后分别为每个文件调用Java虚拟机进行运行测试。 (看不太懂...)
     */

    /**
     * 方法3: junit
     */

    /**
     *  方法4: 日志代理 (很厉害！)
     *  直接窃取方法调用，并进行日志录，然后调用超类中的方法！！！
     */
    public void method4(){
        Random generator1 = new Random(); //一般都这么写
        //但其实还能这么写↓
        Random generator2 = new Random(){
            public double nextDouble(){
                double result = super.nextDouble();
                //在这里我记录下日志^_^
                LOGGER.info("有人调用Random的nextDouble方法啦: " + this.toString());
                return result;
            }
        };
        //让我们来试试看  TODO 这里还可以进行堆栈跟踪，看看到底是谁调用了nextDouble()这个方法
        generator2.nextDouble();
    }

    /**
     *  方法5: 利用Throwable类提供的printStackTrace()方法，可以从任何一个异常对象中获得堆栈情况！
     */
    public void method5() throws Throwable{
        //下面的代码将捕获任何异常，打印异常对象和堆栈跟踪，然后重新抛出异常，以便能够找到相应的处理器
        try{
            //...
        }catch(Throwable t){ //Throwable是Exception和Error类的父类
            t.printStackTrace();  //打印异常对象和堆栈信息  (TODO 为什么不用StackTraceElement呢)
            throw t; //抛出，让相应处理器去解决
        }

        //文中说不一定要通过捕获异常来生成堆栈跟踪。只要在代码中插入下面这套语句就可以获得堆栈跟踪:
        Thread.dumpStack(); //1. 这句话只用于debug 2.打印当前线程的堆栈跟踪信息至"标准错误流"。3. 然后使用Thread.getAllStackTraces()获取所有的堆栈跟踪信息。
    }

    /**
     *  方法6:一般来说，堆栈跟踪显示在System.err上。也可以利用printStackTrace(PrintWriter s)方法将它发送到一个文件中。
     *        当然，还能把堆栈跟踪捕获到一个字符串当中。
     */
    public void method6(){
        //1. 将堆栈跟踪显示在System.err上  TODO 这句话根本就没理解
        //2. 利用printStackTrace(PrintWriter s)方法将它发送到一个文件中。  参考Test9

        //[重点关注]
        //我们可以使用Thread.dumpStack(); 这句话其实就等同于 new Exception("Stack trace").printStackTrace();
        Thread.dumpStack();
        PrintStream ps = System.err;
        //1. 直接作为字符串来输出
        //       可以发现，输出的结果包括以下三步:
        //       1. at java.lang.Thread.dumpStack(Thread.java:1329)  本Thread.dumapStack()被调用处
        //       2. at com.mmr.learn.exception.Test8.method6(Test8.java:100)
        //       3. at com.mmr.learn.exception.Test8.main(Test8.java:26)
        //System.out.println(ps.toString());

        //2. 写入目标文件
        try {
            String filePath = System.getProperty("user.dir")  + File.separator + "logs/learning/stackErr/err.txt";
            PrintStream ps2 = new PrintStream(filePath);
            System.setErr(ps2); //重定向标准错误流至至文件中(原本重定向至控制台)
            System.err.println("你好哦，以下是堆栈跟踪内容\n " +  ps.toString()); //这里利用到了"重定向"，重定向了标准错误流, 这里的ps.toString貌似有问题
        } catch (FileNotFoundException e) { //由于文件路径不是由外部传入，而且这里也只不过是做测试，因此不需要将异常抛出
            e.printStackTrace();
        }
    }

    /**
     *  方法11
     */

    /**
     *  方法12: 可以使用jmap工具获得一个堆的转储
     */



    /*
        Thread Dump详解 (由于搞不懂Thread.dumpStack()，因此学习一下)  https://blog.csdn.net/rachel_luo/article/details/8920596
        1. 什么是ThreadDump
     */
}


