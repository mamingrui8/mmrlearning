package com.mmr.learn.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-25
 * Time: 15:55
 * 通过这个例子可以看出，finally优先，finally中的return将finally子句中的return给覆盖了
 */
public class Test2 {
    public static void main(String[] args){
        System.out.print(f(2));
    }

    public static int f(int i){
        try{
            int k = i * i;
            return k;
        }finally{
            if(i == 2) return 0;
        }
    }
}
