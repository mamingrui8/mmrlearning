package com.mmr.learn.thread.lesson.lesson14.t1;

public class ThreadB extends Thread{
    private MyList list;

    public ThreadB(MyList list){
        super();
        this.list = list;
    }

    @Override
    public void run(){
        try{
            while(true){
                if(list.size() == 5){
                    System.out.println("==5 了，线程b要退出了!  ");
                    throw new InterruptedException();
                }
//                Thread.sleep(500);
//                System.out.println("b线程，list的长度: " + list.size());
            }
        } catch (InterruptedException e){
           e.printStackTrace();
        }
    }
}
