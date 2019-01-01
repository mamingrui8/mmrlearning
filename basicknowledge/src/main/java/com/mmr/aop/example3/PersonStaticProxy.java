package com.mmr.aop.example3;

/**
 * Description: 静态代理
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月16日 18:34
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class PersonStaticProxy {
    private IPerson iPerson;

    public PersonStaticProxy(IPerson iPerson){
        this.iPerson = iPerson;
    }

    public void doSomething(){
        System.out.println("before proxy");
        iPerson.doSomething();
        System.out.println("after proxy");
    }

    public static void main(String[] args) {
        PersonStaticProxy personStaticProxy = new PersonStaticProxy(new PersonImpl());
        personStaticProxy.doSomething();
    }
}
