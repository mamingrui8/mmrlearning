package com.mmr.learn.proxy.cglib.demo;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author mamr
 * @date 2020/9/6 18:35
 */
public class MyMethodInterceptor implements MethodInterceptor {

    /**
     * 拦截器
     * 这个方法只会在代理对象的方法被调用时触发
     *
     * @param sub 被增强后的对象，继承了实际主题角色
     * @param method 被拦截的方法
     * @param args   被拦截方法的参数
     * @param methodProxy 用于触发(调用)父类方法的代理(方法)对象
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object sub, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("============插入前置通知============");
        // 通过methodProxy，我们就好像是在直接调用HelloService内的方法
        Object resultObject = methodProxy.invokeSuper(sub, args);
        System.out.println("============插入后置通知============");
        return resultObject;
    }
}
