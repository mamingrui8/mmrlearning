package com.mmr.reflect;

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
            Class<?> clazz = Class.forName("com.mmr.reflect.A");
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
             *  若没有写setNumber.invoke();  TODO 此处没有写完:  https://blog.csdn.net/lazycatw/article/details/42239707
             */
            setNumber.invoke(instance, 123);
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