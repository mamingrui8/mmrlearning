package com.mmr.learn.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 拦截器 用于方法的拦截和回调
 *
 * @author mamr
 * @date 2020/11/17 22:50
 */
public class LogInterceptor implements MethodInterceptor {

    /**
     * @param realObject  要进行增强的对象 原始对象 (比如当前案例中的UserDao)
     * @param method      需要拦截的方法
     * @param args        参数列表，如果是基本数据类型，那就需要传入封装类型，如int->Integer, long-Long, double->Double
     * @param methodProxy 对方法的代理
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object realObject, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before();
        // methodProxy.invoke() 执行的是子类的方法  不能使用这个方法，否则会导致死循环
        // methodProxy.invokeSuper() 执行的是原始类的方法
        Object result = methodProxy.invokeSuper(realObject, args);
        after();
        return result;
    }

    private void before() {
        System.out.println(String.format("log start time [%s] ", LocalDateTime.now()));
    }

    private void after() {
        System.out.println(String.format("log end time [%s] ", LocalDateTime.now()));
    }
}
