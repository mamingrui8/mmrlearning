package com.mmr.learn.proxy.cglib.demo;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author mamr
 * @date 2020/9/6 18:39
 */
public class Client {
    public static void main(String[] args) {
        // 代理类class文件存入本地磁盘方便我们反编译查看源码  若不存在指定目录，则会自动创建目录(递归创建)
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code\\mmr");
        // 通过cglib动态代理创建代理对象 此时代理对象没有任何的意义
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(HelloService.class);
        // 设置enhancer的回调对象  必须设置，否则抛出NullPointException
        enhancer.setCallbacks(new Callback[] {new MyMethodInterceptor()});

        // 创建代理对象
        HelloService proxy = (HelloService) enhancer.create();
        // 通过代理对象调用目标方法
        proxy.test1();

        /*
            代理对象继承了HelloService，它对父类中所有能被覆盖的方法全部进行了重写

              public final String test3()
              {
                MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
                if (tmp4_1 == null)
                {
                  tmp4_1;
                  CGLIB$BIND_CALLBACKS(this);
                }
                MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
                if (tmp17_14 != null)
                  // 执行方法拦截器 将拦截器返回的结果，返回给调用者
                  return (String)tmp17_14.intercept(this, CGLIB$test3$0$Method, CGLIB$emptyArgs, CGLIB$test3$0$Proxy);
                return super.test3();
              }

            可以看到，this.CGLIB$CALLBACK_0就是回调方法，它被赋值给方法的拦截器。 （拦截器=回调方法）
            如果回调方法为空，则会为当前代理类绑定一个回调方法，否则直接执行拦截器
         */

        /*
            得出结论:
            1. 想要实现cglib动态代理的类必须能被继承(不能被申明为final)
            2. 想要被代理的方法可以是final的，由于final的方法不能被覆盖，因此无法受到方法拦截器的控制。
            3. 通过调用方法拦截器中的interceptor()方法，再借助methodProxy.invokeSuper(sub, args);实现了对实际主题角色的目标方法的调用。
         */
    }
}
