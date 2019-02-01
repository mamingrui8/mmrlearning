package com.mmr.learn.aop.example3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月16日 18:48
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class PersonDynamicProxy implements InvocationHandler {
    private Object delegate;

    public Object bind(Object delegate){
        this.delegate = delegate;
        Class[] interfaces = delegate.getClass().getInterfaces();//实现的所有接口
        //直接获取代理类对象
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try{
            System.out.println("Before Proxy");
            method.invoke(delegate,  args);
            System.out.println("After Proxy");
        }catch(Exception e){
            throw e;
        }
        return result;
    }

    public static void main(String[] args) {
        PersonDynamicProxy personDynamicProxy = new PersonDynamicProxy();
        IPerson iPerson = (IPerson) personDynamicProxy.bind(new PersonImpl());
        Objects.requireNonNull(iPerson);
        iPerson.doSomething();
        System.out.println("--------------分割线--------------");
        iPerson.saySomething();

    }
}
