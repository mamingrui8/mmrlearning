package com.mmr.learn.thread.lesson7;

/**
 * Description: 将任意对象作为对象监视器
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 14:09
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
        Java支持 对"任意对象"作为"对象监视器"来实现同步功能。这个"任意对象"大多数指的是实例变量以及方法的参数，使用格式为"synchronized(非this对象 x)"

        总结:
        1. 多个线程持有同一个对象监视器的情况下，同一时间只能有一个线程可以执行synchronized(非this对象 x)同步代码块中的代码。

        优点: 如果一个类中有许多synchronized方法，这时虽然能实现同步，但也会造成阻塞。如果把synchronized放在非this对象上，也就是锁定非this对象，则synchronized(非this)代码块与其他同步方法可以异步执行。
              不与其他锁this的同步方法争抢this锁，大大的提高效率
              (原因十分简单，毕竟锁定的不是同一个对象)

        syncblockstring2项目中，ThreadA持有的对象锁是anyString(String类型)  ，哪怕你写成this.anyString，它仍然持有的是anyString而不是this对象
        而ThreadB持有的对象锁是this(因为synchronized被加在成员方法上)，由于锁互不相同，因此异步执行。

        public class Service {
            private String usernameParam;
            private String passwordParam;
            private String anyString = new String();
            public void setUsernamePassword(String username, String password){
                try{
                    synchronized (anyString){
                        //TODO 由于anyString是实例变量，属于类的对象，因此synchronized (anyString) 等价于 synchronized (this.anyString)  也即，锁在对象身上
                        //上述理解是错的！哪怕写成this.anyString,对象监视器仍然是anyString 而不是Service
                        //之所以这种写法会导致同步，是因为多个线程持有同一个对象监视器了，只不过之前的对象监视器是this，现在的对象监视器是andString

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


        改良后:
        public class Service {
            private String usernameParam;
            private String passwordParam;
            public void setUsernamePassword(String username, String password){
                try{
                    String anyString = new String();
                    synchronized (anyString){ //anyString属于方法，存放在栈中，不属于对象。因此ThreadA和TheadBzhendui 针对这段代码会异步执行
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


     */
}
