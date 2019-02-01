package com.mmr.learn.exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-25
 * Time: 17:01
 */
public class Test5 {

    /**
     * 构造一个抛出异常的方法
     */
    public static void throwableException() throws Exception{
        try{
            File file = new File("12323323");
            FileInputStream fis = new FileInputStream(file);
        }catch (FileNotFoundException e){
            //[注意]此处开始封装异常
            Throwable se = new Throwable(e);
            se.initCause(e); //将e设置成诱饵
            try {
                throw se;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    /**
     * 重新获取原始类型的异常
     */
    public static void main(String[] args){
        try{
            throwableException();
        }catch(Exception se){
            System.out.println("=======直接输出异常=======");
            System.out.println(se.getMessage());
            System.out.println("=======输出原始异常=======");
            Throwable e  = se.getCause();
            System.out.println(e.getMessage());
        }

    }

}
