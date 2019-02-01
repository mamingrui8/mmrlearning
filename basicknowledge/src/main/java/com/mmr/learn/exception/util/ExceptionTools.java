package com.mmr.learn.exception.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-29
 * Time: 15:48
 */
public class ExceptionTools {

    /**
     *  获取异常的堆栈调用信息
     * @Return 调用的堆栈
     */
    public static String getStackTrace(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        /*
            1. StringWriter 是Writer的包装类，主要用于那些需要操纵String,但又必须使用Writer来作为参数传递参数的场景
         */
        try{
            e.printStackTrace(pw); //将堆栈信息输出到pw中

            return sw.toString();
        }catch(Exception e1){
            return "";
        }
    }

}
