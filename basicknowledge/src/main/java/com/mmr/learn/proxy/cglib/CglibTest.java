package com.mmr.learn.proxy.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

/**
 * Cglib动态代理测试类
 *
 * @author mamr
 * @date 2020/11/17 22:57
 */
public class CglibTest {
    public static void main(String[] args) {
        LogInterceptor1 interceptor1 = new LogInterceptor1();
        LogInterceptor2 interceptor2 = new LogInterceptor2();
        DefaultInterceptor defaultInterceptor = new DefaultInterceptor();

        Enhancer enhancer = new Enhancer();

        // 设置需要增强的类 由于cglib动态代理是通过继承的方式实现的，所以需要把待增强的类作为超类
        enhancer.setSuperclass(UserDao.class);

        // 设置方法执行时的拦截器  (就是方法增强)
        enhancer.setCallbacks(new Callback[]{interceptor1, interceptor2, defaultInterceptor});

        // 当指定了多个拦截器时，必须额外指定过滤器，否则报错:
        // java.lang.IllegalStateException: Multiple callback types possible but no filter specified
        // 换句话说，一个方法不能走多个过滤器，不能形成过过滤器链 (类似责任链模式)
        enhancer.setCallbackFilter(new DaoFilter());


        // 创建动态代理对象
        UserDao proxy = (UserDao) enhancer.create();
        proxy.update();
        proxy.select();

        proxy.unEnhance();
    }
}
