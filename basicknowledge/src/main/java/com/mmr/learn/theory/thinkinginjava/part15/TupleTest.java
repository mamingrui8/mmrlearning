package com.mmr.learn.theory.thinkinginjava.part15;

import net.mindview.util.TwoTuple;

import static com.mmr.learn.theory.thinkinginjava.part15.Tuple.*;

/**
 * 元组工具类的测试类
 * @author Charles Wesley
 * @date 2019/11/24 17:49
 */
public class TupleTest {
    /**
     * @return 返回的是一个参数化的TwoTuple对象
     */
    static TwoTuple<String, Integer> f(){
        return tuple("hi", 66);
    }

    /**
     * @return 返回的是一个非参数化的TwoTuple对象
     * 在某种意义上讲，f2()的返回值被向上转型为一个非参数化的TwoTuple
     */
    static TwoTuple f2(){
        return tuple("hi", 66);
    }

    static <A, B> void ff(TwoTuple<A, B> twoTuple){
        System.out.println(twoTuple);
    }

    static void ff2(TwoTuple<String, Integer> twoTuple){
        System.out.println(twoTuple);
    }

    public static void main(String[] args) {
        /*
            f2()返回的是一个非参数的TwoTuple对象，编译器并没有关于f2()的警告信息，因为我们没有将其返回值作为"参数化对象"来使用。
            如果将其作为"参数化对象"来使用，会出现什么现象呢？请看下例:
         */
        ff(f2()); //编译器会报出警告:  unchecked assignment
        ff2(f2()); //编译器会报出警告:  unchecked assignment
        ff(f());
    }
}
