package com.mmr.learn.thread.lesson.lesson1;

import java.util.concurrent.TimeUnit;

/**
 * Description: 线程不安全的第二个示例
 * 解决非线程安全的手段:  synchronized
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月02日 17:43
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Thread2 {
    public static void main(String[] args){
        Thread thread1 = new Thread(new ALogin(), "A");
        Thread thread2 = new Thread(new BLogin(), "B");

        thread1.start();
        thread2.start();
    }
}

class LoginServlet{
    private static String usernameRef;
    private static String passwordRef;
    synchronized public static void post(String username, String password){ //如果把synchronized加在此处，相当于锁定了整个LoginServlet类下的所有对象
        try{
            usernameRef = username; //由A执行至此时会休眠2秒，接着B把b赋值给了usernameRef，A睡醒后，usernameRef的值当然就发生了改变。
            if(username.equals("a")){
                TimeUnit.SECONDS.sleep(2); //sleep并不会释放锁，线程仍然可以同步控制，因此不会让出系统资源。
            }
            passwordRef = password;
            System.out.println("username->" + usernameRef + ",password->" + passwordRef);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

class ALogin extends Thread{
    @Override
    public void run(){
        LoginServlet.post("a", "aa");
    }
}

class BLogin extends Thread{
    @Override
    public void run(){
        LoginServlet.post("b", "bb");
    }
}