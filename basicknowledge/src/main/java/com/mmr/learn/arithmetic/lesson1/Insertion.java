package com.mmr.learn.arithmetic.lesson1;

/**
 * 插入排序
 */
public class Insertion {
    public static Integer compareNum = 0;

    public static Integer exchNum = 0;
    /**
     * 排序算法 具体实现
     */
    public static void sort(Comparable[] a){
        int length = a.length;
        for(int i =1; i<a.length; i++){
            for(int j = i; j>0 && less(a[j], a[j-1]); j--){ //如果不满足条件，则for循环会直接被终止  因此，插入排序利用了之前的排序结果
                exch(a, j, j-1);
            }
        }
    }

    /**
     * 前者是否比后者小
     */
    private static boolean less(Comparable v, Comparable w){
        compareNum++;
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
        exchNum++;
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
        //Integer[] target = new Integer[]{1,5,2,3,9,6};
        Integer[] target = new Integer[]{1, 2, 3, 4, 5};
        sort(target);
        show(target);
        System.out.println("compareNum->" + compareNum);
        System.out.println("exchNum->" + exchNum);
    }
}
