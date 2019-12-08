package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 有响应的用户界面
 * Tips: 如果直接运行UnresponsiveUI，想必除了杀死进程，否则不可能终止程序。
 *       想要在运行时还能监控用户的输入，就必须把运算放到run()中，让出处理器给别的程序。
 *       这里之所以为ResponsiveUI设置成守护线程，是为了在nmain线程执行完毕后，ResponsiveUI线程也能随之结束，否则while()循环将永远执行下去。
 * 疑问:
 * 1. 在非后台线程执行完System.in.read()，等待用户输入的过程中，后台线程能否正常运行？线程调度器会不会将CPU时间片给予后台线程?
 *    答: 会！System.in.read();是一个阻塞方法，当线程阻塞后，线程调度器会将CPU分配给其它线程！
 * @author Charles Wesley
 * @date 2019/12/8 18:11
 */
public class ResponsiveUI extends Thread{
    private static volatile double d = 1;
    private static int count = 0;
    public ResponsiveUI() {
        this.setDaemon(true);
        this.start();
    }
    public void run() {
        while (true) {
            d = d + (Math.PI + Math.E) /d;
            count++;
        }
    }
    public static void main(String[] args) throws Exception{
        new ResponsiveUI();
        System.in.read();
        System.out.println(d);
        System.out.println(count);
    }
}

/**
 * 一个过于专注计算的界面
 */
class UnresponsiveUI {
    private volatile double d = 1;
    public UnresponsiveUI() throws Exception{
        while(d > 0) {
            d = d + (Math.PI + Math.E) /d;
        }
        //d > 0这个条件骗过了编译器，实际上，System.in.read()这条语句永远也得不到执行
        System.in.read();
    }
}