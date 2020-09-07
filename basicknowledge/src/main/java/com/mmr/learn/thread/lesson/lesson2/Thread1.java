package com.mmr.learn.thread.lesson.lesson2;

import java.util.concurrent.TimeUnit;

/**
 * Description: 多个对象多把锁
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月14日 14:43
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Thread1 {
    /**
     * 只有共享资源的读写访问才需要同步化。
     * 比如test1()中，传入athread和bthread是同一个对象时才有共享的意义。
     * @param args
     */
    public static void main(String[] args){
        Thread1 thread1 = new Thread1();
        thread1.test1();
    }

    /**
     * 虽然将锁加在addI()上，但实际上锁被加到了对象上。而aThread和bThread分别持有来自同一个对象的两把锁，互不干扰，因此异步执行代码。
     */
    public void test1(){
        HasSelfPrivateNum numRef1 = new HasSelfPrivateNum();
        HasSelfPrivateNum numRef2 = new HasSelfPrivateNum();
        Thread1_1 athread = new Thread1_1(numRef1);
        athread.start();
        Thread1_2 bthread = new Thread1_2(numRef2);
        bthread.start();
    }

    /**
     * 验证在方法上加的锁是否加到了对象身上
     */
    public void test2(){

    }
}

class HasSelfPrivateNum{
    private int num = 0;

    synchronized public void addI(String username){
        try{
            if ("a".equals(username)) {
                num = 100;
                System.out.println("a set over!");
                TimeUnit.SECONDS.sleep(2);
            }else{
                num = 200;
                System.out.println("b set over!");
            }
            System.out.println(username + " num=" + num);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class Thread1_1 extends Thread{
    private HasSelfPrivateNum numRef;

    public Thread1_1(HasSelfPrivateNum numRef){
        super();
        this.numRef = numRef;
    }

    @Override
    public void run(){
        super.run();
        numRef.addI("a");
    }
}

class Thread1_2 extends Thread{
    private HasSelfPrivateNum numRef;

    public Thread1_2(HasSelfPrivateNum numRef){
        super();
        this.numRef = numRef;
    }

    @Override
    public void run(){
        super.run();
        numRef.addI("b");
    }
}
