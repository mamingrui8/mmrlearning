package com.mmr.learn.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * Description: 本文旨在讲解 反射中 setAccessible方法的含义和作用
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月15日 16:48
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class SetAccessibleTest {
    public static void main(String[] args){
        try {
            //看看Accessible()究竟是什么
            Method getNumberMethod = A.class.getDeclaredMethod("getNumber");
            //注意此处: int类型的属性或包含基础数据类型的方法同样可以被反射，只不过方法参数的类型需要调整为封装类型.TYPE
            Method setNumberMethod = A.class.getDeclaredMethod("setNumber", Integer.TYPE);

            System.out.println("getNumber isAccessible(): " + getNumberMethod.isAccessible());
            System.out.println("setNumber isAccesible(): " + setNumberMethod.isAccessible());

            System.out.println("下面开始通过反射来创建对象以及为对象赋值！");
            Class<?> clazz = Class.forName("com.mmr.learn.reflect.A");
            /**
             * getDeclaredField()和getField()的区别:
             * 1. getDeclaredField():
             *    checkMemberAccess(sm, Member.DECLARED, Reflection.getCallerClass(), true);
             *    只要是被申明(declared)的字段都能获取到。
             * 2. getField():
             *    checkMemberAccess(sm, Member.PUBLIC, Reflection.getCallerClass(), true);
             *    当且仅当访问类型为public的字段才能被获取到。
             */
            Field numberField = clazz.getDeclaredField("number");
            System.out.println("名为\"number\"的属性: " + numberField);
            Method getNumber = clazz.getMethod("getNumber"); //由于getNumber被public修饰，所以直接用getMethod()
            System.out.println("getNumber: " + getNumber);
            Method setNumber = clazz.getDeclaredMethod("setNumber", Integer.TYPE); //由于setNumber被private修饰，所以必须用getDeclaredMethod()才行
            System.out.println("setNumber: " + setNumber);

            Object instance = clazz.newInstance();
            /**
             * 【重点】
             *  若没有写setNumber.setAccessible(true); 执行setNumber.invoke()方法时将会报错如下:
             *  java.lang.IllegalAccessException: class com.mmr.learn.reflect.SetAccessibleTest cannot access a member of class com.mmr.learn.reflect.A with modifiers "private"
             *  原因在于 setNumber()的访问类型是private,只有对象内部可见，作为反射类的com.mmr.reflect.SetAccessibleTest 无法获取这个方法，从而无法执行方法。
             *
             *  setAccessible() 并没有改变方法的访问级别，而是使jdk放弃了修饰符的检查机制。//TODO 此处用到了jdk9中的模块系统，具体请看com.mmr.jdk9
             */
            setNumber.setAccessible(true);
            setNumber.invoke(instance, 123);

            System.out.println(getNumber.invoke(instance));
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

class A{
    private int number;

    public int getNumber() {
        return number;
    }

    private void setNumber(int number) {
        this.number = number;
    }
}