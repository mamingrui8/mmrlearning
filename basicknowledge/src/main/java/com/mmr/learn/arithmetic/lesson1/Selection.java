package com.mmr.learn.arithmetic.lesson1;

/**
 * 选择排序
 *
 * 交换的总次数是 N  算法的时间效率取决于比较的次数
 */
public class Selection {
    /**
     * 排序算法 具体实现
     */
    public static void sort(Comparable[] a){
        int n = a.length; //数组的长度
        for(int i=0; i< n; i++){
            int minIndex = i; //本轮最小数值的下标
            for(int j = i+1; j<n; j++){
                if(less(a[j], a[minIndex]))
                    minIndex = j;
            }
            exch(a, i, minIndex);
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
            System.out.print(a[i] + ",");
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
        Integer[] target = new Integer[]{1,5,2,3,9,6};
        sort(target);
        show(target);
    }
}
