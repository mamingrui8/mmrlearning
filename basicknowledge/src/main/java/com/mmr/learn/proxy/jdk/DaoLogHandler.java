package com.mmr.learn.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义请求程序处理类
 *
 * @author mamr
 * @date 2020/9/5 14:22
 */
public class DaoLogHandler implements InvocationHandler {
    private Object object;

    public DaoLogHandler() {

    }

    public DaoLogHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeInvoke();
        Object result = method.invoke(object, args);
        afterInvoke();
        return result;
    }

    /**
     * 记录方法的调用时间
     */
    public void beforeInvoke() {
        System.out.println("调用时间: " + LocalDateTime.now());
    }

    public void afterInvoke() {
        System.out.println("方法调用结束");
    }
}
