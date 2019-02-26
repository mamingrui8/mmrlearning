package com.mmr.learn.aop.example3;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2018年12月16日 20:00
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class PersonCGLIBProxy implements MethodInterceptor {
    private Object delegate;

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before proxy");
        Object result = methodProxy.invokeSuper(method, args);
        System.out.println("After proxy");
        return result;
    }

    public static PersonImpl getProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PersonImpl.class);

        enhancer.setCallback(new PersonCGLIBProxy());
        return (PersonImpl)enhancer.create();
    }

    public static void main(String[] args) {
        PersonImpl pp =  PersonCGLIBProxy.getProxyInstance();
        pp.doSomething();
    }
}

