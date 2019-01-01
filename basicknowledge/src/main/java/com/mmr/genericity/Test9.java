package com.mmr.genericity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-09-10
 * Time: 23:32
 */
public class Test9 {
    /*
        之前谈了那么多泛型域、泛型方法都存储在普通类种，那么问题来了: 能否在泛型类中使用类型变量T呢？
        目前学到了12.6.6
     */


}

/**
 *  实验用的泛型类
 */
class Singleton<T>{
    //private static T singleton;  //ERROR
    //private static T getSingleInstance(){} //ERROR

}
