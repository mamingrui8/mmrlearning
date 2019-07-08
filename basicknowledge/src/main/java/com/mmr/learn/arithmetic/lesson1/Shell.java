package com.mmr.learn.arithmetic.lesson1;

/**
 * 希尔排序
 */
public class Shell {
    /**
     * 排序算法 具体实现
     */
    public static void sort(Comparable[] a){
        int N = a.length;
        int h = 1;
        while(h < N/3) h = 3*h +1; //1,4,13,40,...
        while(h >= 1){
            for(int i = h; i < N; i++){
                for(int j = i; j>=h && less(a[j], a[j-h]); j-=h){
                    exch(a, j, j-h);
                }
            }
            show(a);
            h = h/3;
        }
    }

    /**
     * 前者是否比后者小
     */
    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    /**
     * 打印数组中的每一个元素
     */
    private static void show(Comparable[] a){
        for(int i=0; i<a.length; i++)
            System.out.print(a[i] + " ");
        System.out.println();
    }

    /**
     * 交换元素
     */
    private static void exch(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * 测试数组中的元素是否有排序
     */
    public static boolean isSorted(Comparable[] a){
        for(int i=0; i<a.length; i++){
            if(less(a[i], a[i-1]))
                return false;
        }
        return true;
    }

    /**
     * 主程序
     */
    public static void main(String[] args) {
        //从标准输入读取字符串，将它们排序并输出
        Integer[] target = {3, 4, 6, 5, 2, 1, 7};
        sort(target);
    }
}
