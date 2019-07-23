package com.mmr.learn.theory.source.java.util.question;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Arrays.copyof() 与 System.arraycopy() 方法的缺陷
 * (see 000001) 自己发现的bug
 * 话说回来，这并不能算bug，毕竟Arrays也只是按规矩办事，该复制几个元素就复制几个元素。
 */
public class Demo2 {

    @Test
    public void test1(){
        String[] ss = new String[]{"1", "2", "3", null, "5"};
        String[] strings = Arrays.copyOf(ss, 4);

        String[] newStrings = new String[4];
        System.arraycopy(ss, 0, newStrings, 0, 4);
        System.out.println(newStrings); //输出的数组中仅含有1,2,3和null

        //这样一来，复制后就丢失了元素5！
        List<String> strings1 = Arrays.asList(ss);
        System.out.println(strings1);
    }

    @Test
    public void test2(){
        ArrayList<String> ss = new ArrayList<>(10);

        for(int i=0; i<10; i++){
            ss.add(i + "");
        }

        ss.add("13");
        ss.trimToSize();

        System.out.println();
    }

    /**
     * Arrays.copyof()是对System.arraycopy()的一道封装，仅仅初始化了目标数组而已。
     * 具体源码见下方
     */

    /**
     * System.arraycopy()
     * @param src 源数组
     * @param srcPos 打算从源数组中复制的起始位置(数组下标)
     * @param dest 目标数组
     * @param destPos 打算向目标数组中复制的起始位置(数组下标)
     * @param length 本次复制数据的长度
     */
    public static native void arraycopy(Object src,  int  srcPos,
                                        Object dest, int destPos,
                                        int length);

    /**
     * Arrays
     * @param original 源数组
     * @param newLength 准备返回的数组的长度
     * @param newType 准备返回的数组的元素类型
     * @param <T> 源数组元素类型
     * @param <U> 目标数组元素类型
     */
    public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        @SuppressWarnings("unchecked")
        T[] copy = ((Object)newType == (Object)Object[].class)
                ? (T[]) new Object[newLength]
                : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }
}
