package com.mmr.learn.classloader;

/**
 * 基础知识
 * @author mamr
 * @date 2020/9/5 17:18
 */
public class Basic {
    public static void main(String[] args) throws ClassNotFoundException {
//        System.out.println("系统类加载器: " + ClassLoader.getSystemClassLoader());
//        System.out.println("系统类加载器的父类: " + ClassLoader.getSystemClassLoader().getParent());
//        System.out.println("系统类加载器的父类的父类: " + ClassLoader.getSystemClassLoader().getParent().getParent()); // 输出为null 这就说明标准扩展类加载器的父类加载器被强制设定为null
//
//        System.out.println("=======================================================================");
//        System.out.println("查看当前系统中包含的路径条目: " + System.getProperty("java.class.path"));
//        Class typeLoaded = Class.forName("classloader.test.bean.TestBBB");
//        System.out.println("查看/调用指定类的类加载器: " + typeLoaded.getClassLoader());

        Class aClass = Thread.currentThread().getContextClassLoader().loadClass("classloader.test.bean.TestBBB");
        System.out.println(aClass.getClassLoader());
    }
}
