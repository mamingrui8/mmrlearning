package com.mmr.learn.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 拦截器 用于方法的拦截和回调
 * @author mamr
 * @date 2020/11/18 7:59 下午
 */
public class LogInterceptor2 implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before();
        methodProxy.invokeSuper(o, args);
        after();
        return null;
    }

    private void before() {
        System.out.println(String.format("[LogInterceptor2] log start time [%s] ", LocalDateTime.now()));
    }

    private void after() {
        System.out.println(String.format("[LogInterceptor2] log end time [%s] ", LocalDateTime.now()));
    }
}
