package com.mmr.learn.thread.lesson.lesson7.syncblockstring;

import java.util.concurrent.TimeUnit;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 14:16
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class Service {
    private String usernameParam;
    private String passwordParam;
    public void setUsernamePassword(String username, String password){
        try{
            String anyString = new String();
            synchronized (anyString){ //anyString属于方法，存放在栈中，不属于对象。ThreadA和TheadB针对这段代码相当于一人拿了一把锁，因此会异步执行
                System.out.println("线程名称为: " + Thread.currentThread().getName() + " 在 " + System.currentTimeMillis() + "进入同步块");
                usernameParam = username;
                TimeUnit.SECONDS.sleep(3);
                passwordParam = password;
                System.out.println("线程名称为: " + Thread.currentThread().getName() + " 在 " + System.currentTimeMillis() + "离开同步块");
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
