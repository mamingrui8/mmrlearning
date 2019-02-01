package com.mmr.learn.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-23
 * Time: 21:41
 */
public class Test1 {
    public static final Logger log = LoggerFactory.getLogger(Test1.class);
    public static void main(String[] args){
        Test1 test1 = new Test1();

        int result = test1.division_correct(6, 0);
        System.out.print(result);
    }

    //由此看出，自定义异常类是无法替代任何java已存在的自有异常类
    //错误用法为什么错？因为它妄图通过自定义异常去捕获系统已存在的异常
    //正确用法为什么对？因为它在异常(算术异常)触发之前，就已经把异常通过人为的方式抛出了，所以可以用自定义异常来处理！

    /**
     * 自定义异常的正确用法
     */
    public int division_correct(int dividend, int divisor) throws MyException{
        if(divisor == 0){
            throw new MyException("除数不能为0");
        }
        if(dividend == 10){
            throw new MyException("被除数不能为10");
        }
        return dividend/divisor;
    }

    /**
     * 自定义异常的错误用法
     */
    public int division_wrong(int dividend, int divisor) throws MyException{
        int result = 0;

        try{
            result = dividend/divisor;
        }catch (MyException e1){
            throw new MyException();
        }
        return dividend/divisor;
    }
}


class FileNotFoundExceptionMMR extends Exception {
    FileNotFoundExceptionMMR(){}
    FileNotFoundExceptionMMR(String message){
        super(message);
    }
}

class MyException extends java.lang.ArithmeticException {
    MyException(){}
    MyException(String message){
        super("犯错误了：" + message);
    }
}

class t1{
    void test1() throws ArithmeticException{}
    void test2() throws IOException{System.out.print("123");}
}

class t2 extends t1{
    //void test1() throws Exception{}  子类中重写的方法不能抛出比超类级别更高的异常
    void test2(){System.out.print("456");}
}