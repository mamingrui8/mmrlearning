package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 如果在代码处处都需要使用相同的异常逻辑器，那么比为每一个线程设置setUncaughtExceptionHandler()方便的做法是:
 * 在Thread类中设置一个静态域，并将这个处理器设置为默认的未捕获异常处理器。
 *
 * 系统会优先检查并使用线程专有的未捕获异常处理器，如果没有专有版本，则系统会去检查线程所在线程组中是否含有uncaughtException()方法，
 * 如果都没有，才会调用defaultUncaughtExceptionHandler
 * @author Charles Wesley
 * @date 2019/12/8 20:21
 */
public class SettingDefaultHandler {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("caught " + e);
            }
        });
    }
}
