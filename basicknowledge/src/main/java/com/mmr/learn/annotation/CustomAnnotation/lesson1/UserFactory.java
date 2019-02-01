package com.mmr.learn.annotation.CustomAnnotation.lesson1;

import java.lang.reflect.Method;

/**
 * Description: 光有注解和被注解注释的类，并不能发挥功效。因此还需要"注解解释器"，本类就是一个注解解释器
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月29日 10:20
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class UserFactory {
    private UserFactory(){}

    public static User generateUser(){
        User user = new User();

        Method[] methods = User.class.getMethods(); //这里不用getDeclaredMethod()是因为Use。类中的方法都是public

        try{
            for (Method method : methods) {
                //如何得知方法是否有注解呢？ 此处给出了答案↓
                boolean flag = method.isAnnotationPresent(Init.class);
                if(flag){
                    Init init = method.getAnnotation(Init.class);
                    if(method.getParameterTypes()[0] == String.class) method.invoke(user, init.value());
                    if(method.getParameterTypes()[0] == Integer.class) method.invoke(user, init.valueAge());
                }
            }
        } catch (Exception e){
           e.printStackTrace();
           return null;
        }
        return user;
    }
}
