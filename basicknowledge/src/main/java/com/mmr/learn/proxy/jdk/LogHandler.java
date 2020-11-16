package com.mmr.learn.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 本类是一个逻辑处理器，目标是对程序进行日志增强 (在原有功能基础上增加日志功能)
 *
 * 必须实现InvocationHandler接口
 * @author mamr
 * @date 2020/11/16 9:22 下午
 */
public class LogHandler implements InvocationHandler {
    /**
     * 在用于增强的逻辑处理器中，我们必须维护一个真实主题角色
     * 不然没有办法调用实际实现的方法
     */
    private Object target;

    public LogHandler(Object target) {
        this.target = target;
    }

    /**
     * 定义了代理对象调用方法时，希望执行的动作，可以集中处理动态代理类的方法调用
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        // 这里就是简单的使用了反射，调用真实主题角色的实际方法
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    /**
     * 调用invoke方法之前执行
     */
    private void before() {
        System.out.println(String.format("log start time [%s] ", LocalDateTime.now()));
    }

    /**
     * 调用invoke方法之后执行
     */
    private void after() {
        System.out.println(String.format("log end time [%s] ", LocalDateTime.now()));
    }
}
