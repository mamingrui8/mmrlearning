package com.mmr.learn.thread.lesson4;

public class PublicVar {
    public String username = "A";
    public String password = "AA";

    //显然，同步方法setValue()的锁属于PublicVar实例
    synchronized public void setValue(String username, String password){
        try{
            this.username = username;
            Thread.sleep(5000); //Thread.sleep()暂时的是当前调用此方法的线程，也就是ThreadA
            this.password = password;
            System.out.println("setValue method thread name= "
                        + Thread.currentThread().getName()
                        + "username=" + username
                        + "password=" + password);
        } catch (InterruptedException e){
           e.printStackTrace();
        }
    }

    public void getValue(){
        System.out.println("getValue method thread name="
                + Thread.currentThread().getName()
                + " username=" + username
                + " password=" + password);
    }
}
