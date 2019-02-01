package com.mmr.learn.annotation.CustomAnnotation.lesson1;

import java.lang.annotation.*;

/**
 * 此注解用于为对象属性赋值
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月29日 10:10
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@Documented //加入至javadoc中，这样生成出的文档包含本注解
@Inherited //注解可被继承
@Target({ElementType.FIELD, ElementType.METHOD}) //作用域在属性或方法上
@Retention(RetentionPolicy.RUNTIME) //保留策略非常宽泛，哪怕是运行时都能通过反射获取到本注解
public @interface Init {
    public String value() default "";
    public int valueAge() default 25; //这里不会自动装箱，因此不能写Integer
}
