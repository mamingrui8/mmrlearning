package com.mmr.learn.thread.lesson.lesson13.synchronizedUpdateNewValue;

/**
 * synchronized代码块具有volatile同步的功能
 */
public class Service {
    private boolean isContinueRun = true;
    public void runMethod(){
            while(isContinueRun){

            }
        System.out.println("停下来了！");
    }
    public void stopMethod(){
        isContinueRun = false;
    }
}
