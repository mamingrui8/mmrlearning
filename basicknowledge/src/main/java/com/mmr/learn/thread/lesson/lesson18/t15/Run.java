package com.mmr.learn.thread.lesson.lesson18.t15;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Run {
    volatile private static int nextPrintWho = 1;
    private static ReentrantLock lock = new ReentrantLock();
    final private static Condition conditionA = lock.newCondition();
    final private static Condition conditionB = lock.newCondition();
    final private static Condition conditionC = lock.newCondition();

    public static void main(String[] args) {
        Thread threadA = new Thread(){
            @Override
            public void run(){
                try{
                    lock.lock();
                    while(nextPrintWho != 1){
                        conditionA.await();
                    }

                    for (int i = 0; i < 3; i++) {
                        System.out.println("ThreadA " + (i + 1));
                    }

                    nextPrintWho = 2;
                    //conditionB.signalAll();
                } catch (Exception e){
                   e.printStackTrace();
                } finally{
                   if(lock.isHeldByCurrentThread()){
                        lock.unlock();
                    }
                }
            }
        };

        Thread threadB = new Thread(){
          @Override
          public void run(){
              try{
                  lock.lock();
                  while(nextPrintWho != 2){
                      System.out.println("陷入沉睡");
                      conditionB.await();
                  }

                  for (int i = 0; i < 3; i++) {
                      System.out.println("ThreadB " + (i + 1));
                  }

                  nextPrintWho = 3;
                  conditionC.signalAll();
              } catch (Exception e){
                  e.printStackTrace();
              } finally{
                  if(lock.isHeldByCurrentThread()){
                      lock.unlock();
                  }
              }
          }
        };

        Thread threadC = new Thread(){
            @Override
            public void run(){
                try{
                    lock.lock();
                    while(nextPrintWho != 3){
                        conditionC.await();
                    }

                    for (int i = 0; i < 3; i++) {
                        System.out.println("ThreadC " + (i + 1));
                    }

                    nextPrintWho = 1;
                    conditionA.signalAll();
                } catch (Exception e){
                    e.printStackTrace();
                } finally{
                    if(lock.isHeldByCurrentThread()){
                        lock.unlock();
                    }
                }
            }
        };

        Thread[] aArray = new Thread[5];
        Thread[] bArray = new Thread[5];
        Thread[] cArray = new Thread[5];

        for (int i = 0; i < 5; i++) {
            aArray[i] = new Thread(threadA);
            bArray[i] = new Thread(threadB);
            cArray[i] = new Thread(threadC);

            aArray[i].start();
            bArray[i].start();
            cArray[i].start();
        }
    }
}
