package com.mmr.learn.arithmetic;

import com.google.common.base.Stopwatch;
import com.mmr.learn.arithmetic.lesson1.Insertion;
import com.mmr.learn.arithmetic.lesson1.Selection;
import org.apache.commons.lang3.time.StopWatch;

/**
 * 比较排序算法的优劣
 */
public class SortCompare {
    public static double time(String alg, Comparable[] a){
        StopWatch stopWatch = new StopWatch();

        if(alg.equals("Insertion")) Insertion.sort(a);
        if(alg.equals("Selection")) Selection.sort(a);

        stopWatch.split();
        return stopWatch.getSplitTime();
    }

    /**
     * 使用算法alg，将T个长度为N的数组排序
     */
    public static double timeRandomInput(String alg, int N, int T){
        double total = 0.0D;
        Double[] a = new Double[N];
        for(int t = 0; t<T; t++){
            //进行一次测试(生成一个数组并排序)
            for(int i=0; i<N; i++)
                a[i] = StdRandom.uniform();
            total += time(alg, a);
        }
        return total;
    }

    public static void main(String[] args) {
        String alg1 = args[0];
        String alg2 = args[1];
        int N = Integer.parseInt(args[2]);
        int T = Integer.parseInt(args[3]);
        double t1 = timeRandomInput(alg1, N, T);
        double t2 = timeRandomInput(alg2, N, T);
        System.out.println(t1);
        System.out.println(t2);
    }
}
