package com.mmr.exception;

import com.learn.learning.exception.util.ExceptionTools;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-29
 * Time: 16:03
 */
public class Test9 {
    public static void main(String[] args){
        try{
            throwCustomizeException();
        } catch (FileNotExistException e){
            System.out.println(e.getExceptionMessage());
        }
    }

    /**
     *  为了做测试，构造一个能够返回FileNotExistException的方法
     */
    private static void throwCustomizeException() throws FileNotExistException {
        throw new FileNotExistException("D:/一份绝对不存在的文件.xls");
    }
}

/**
 *  自定义异常---文件不存在
 */
class FileNotExistException extends Exception{
    /**
     * 文件完整的路径
     */
    private String filePath;

    public FileNotExistException(String filePath){
        this.filePath = filePath;
    }

    public String getExceptionMessage(){
        return "filePath: " + filePath + "|exception trace: " + ExceptionTools.getStackTrace(this);
    }
}

