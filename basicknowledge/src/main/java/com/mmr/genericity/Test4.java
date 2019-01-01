package com.mmr.genericity;

import com.mmr.genericity.entity.Pair;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-29
 * Time: 16:57
 */
public class Test4 {
    public static void main(String[] args){

    }
}

class ArrayAlg
{
    /**
     *  Gets the minium and maxium of an array of object of type T.
     */
    public static <T extends Comparable> Pair<T> minAndMax(T[] a){
        if(a == null || a.length == 0)return null;
        T min = a[0];
        T max = a[0];
        for(int i = 1; i<a.length;i++){
            if(min.compareTo(a[i]) > 0) min = a[i];
            if(max.compareTo(a[i]) < 0) max = a[i];
        }
        return new Pair<>(min, max);
    }
}
