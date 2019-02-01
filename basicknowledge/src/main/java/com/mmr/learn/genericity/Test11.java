package com.mmr.learn.genericity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-09-11
 * Time: 23:15
 */
public class Test11 {

    public static <T extends Throwable> void doWork(T t) throws T{
        try{
            System.out.print("xxx");
        }catch(Throwable realCause){ //如果写成 T e 就会报错: Cannot catch type parameter
            t.initCause(realCause);
            throw t;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwAs(Throwable e) throws T{
        throw (T)e;
    }


    /*
        下面介绍一个疯狂的方法: 将已检查异常包装成未检查异常！！！ P544
     */

}
