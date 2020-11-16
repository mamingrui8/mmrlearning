package com.mmr.learn.proxy.jdk;

import com.mmr.learn.proxy.UserService;
import com.mmr.learn.proxy.UserServiceImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * Jdk动态代理的客户端  使用动态代理对象的消费者
 * @author mamr
 * @date 2020/11/16 9:27 下午
 */
public class JdkProxyClient {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 为了将接下来由JDK动态代理生成的class文件保存到本地，我们使用以下配置↓
        // 如果不确定完整配置名称，可以查找ProxyGenerator的 saveGeneratedFiles 相关代码
        // System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        // 1. 创建被代理的对象
        UserService userService = new UserServiceImpl();
        // 2. 获取对应的ClassLoader
        ClassLoader classLoader = userService.getClass().getClassLoader();
        // 3. 获取被代理的对象对应类中所有接口的Class      由于UserServiceImpl只实现了一个接口，所以我们只能获取到UserService
        Class[] interfaces = userService.getClass().getInterfaces();
        // 4. 创建一个准备传递给代理类的"调用请求处理器"，用于处理代理对象上的方法调用。
        //    注意: 一定要传入实际的执行对象
        InvocationHandler logHandler = new LogHandler(userService);
        // 5. 根据上面提供的信息，创建代理对象，在这个过程中，至少会发生以下操作:
        //    a. JDK会根据传入的参数信息动态的在内存中创建与.class文件等同的字节码(字节流)  相当于创建一份java文件，编译成class后，读取到内存中
        //    b. 根据字节码转换成对应的class
        //    c. 调用newInstance()创建动态代理实例
        UserService proxy = (UserService)Proxy.newProxyInstance(classLoader, interfaces, logHandler);

        // Proxy static boolean isProxyClass(Class<?> cl)  返回cl是否为一个代理类
        System.out.println("proxy是否是一个代理类呢? 答: " + Proxy.isProxyClass(proxy.getClass()));

        // static Class<?> getProxyClass(ClassLoader loader, Class<?>... interfaces) 返回指定接口的代理类
        Class<?> proxyClass = Proxy.getProxyClass(classLoader, interfaces);
        System.out.println("不含方法增强的代理类: " + proxyClass);

        // 注意: 动态代理对象没有无参构造函数，所以不能直接通过Class newInstance()来创建
        // Constructor con = proxyClass.getConstructor(InvocationHandler.class);
        // System.out.println("不含方法增强的代理类的调用处理器(不含你妹啊，在调用构造函数的时候，又把调用处理器传进去了...): "
        // + Proxy.getInvocationHandler(con.newInstance(logHandler)));

        System.out.println("含方法增强的代理类的调用处理器: " + Proxy.getInvocationHandler(proxy));

        // 调用代理方法
        proxy.select();
        proxy.update();

        // 保存JDK动态代理生成的代理类，类名保存为UserServiceMMrProxy
        ProxyUtil.generateClassFile(userService.getClass(), "UserServiceMMrProxy");
    }
}
