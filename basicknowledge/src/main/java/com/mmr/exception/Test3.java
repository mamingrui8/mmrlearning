package com.mmr.exception;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-25
 * Time: 16:09
 * 分析 堆栈跟踪元素
 */
public class Test3 {
    public static void main(String[] args){

    }

    /**
     * 若想分析某个指定线程的堆栈数据，则直接使用e.getStackTrace()
     */
    public static void LearnStackTraceElement(){
        try{
            File file = new File("1232323232323232");
            FileInputStream fs = new FileInputStream(file);
        }catch(Exception e){
            StackTraceElement[] ss = e.getStackTrace();
            for(StackTraceElement s: ss){
                int lineNumber = s.getLineNumber();  //行号
                String fileName = s.getFileName();
                System.out.print(fileName);
                System.out.print(s.toString());
            }
        }
    }

    /**
     * 若不清楚到底是哪个线程出了问题，我们可以使用Thread.getAllStackTraces()获取到所有线程的堆栈信息
     */
    public static void LearnGetAllStackTrace(){
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for(Thread t: map.keySet()){ //首先获取到所有的key，然后遍历key~
            StackTraceElement[] ss = map.get(t); //遍历每一个线程的堆栈信息
            //然后就可以愉快的使用StackTraceElement了
        }
    }




}
