package com.mmr.learn.theory.thinkinginjava.part9;

import java.util.Arrays;

/**
 * 嵌套接口
 */
public class NestingInterfaces {
    public class BImp implements A.B {
        public void f() {}
    }

    class CImp implements A.C {
        public void f() {}
    }

    //不能实现一个类中私有的接口
//    class DImp implements A.D{
//
//    }

    class EImp implements E {
        public void g(){}
    }
    class EImp2 implements E.G {
        public void f() {}
    }
    public static void main(String[] args) {
        //这里研究getD()的返回值的可见性
        A a = new A();

        //第一次尝试————失败
        //A.D是私有的，就算是A的对象都没办法访问和控制
        //! A.D d = a.getD();

        //第二次尝试————失败
        //尝试向下转型失败
        //!A.DImp2 a2 = a.getD();

        //唯一的办法就是把返回值交给有权使用它的对象
        A a2 = new A();
        a2.receiveD(a.getD());
    }
}

class A{
    /**
     * 包访问权限
     */
    interface B {
        void f();
    }
    public class BImp implements B {
        public void f() {}
    }
    public class BImp2 implements B {
        public void f() {}
    }

    /**
     * 拥有public访问权限
     */
    public interface C {
        void f();
    }
    class CImp implements C {
        public void f() {}
    }
    private class CImp2 implements C {
        public void f() {}
    }

    /**
     *  private私有访问权限
     */
    private interface D {
        void f();
    }
    private class DImp implements D{
        public void f() {}
    }
    public class DImp2 implements D{  //即便DImp2被定义成public，它仍然只能在A类的内部或A的对象使用
        public void f() {}
    }

    public D getD() { return new DImp2(); }
    private D dRef;
    public void receiveD(D d) {
        dRef = d;
        dRef.f();
    }

    class F {

    }
}

interface E {
    interface G {
        void f();
    }
    public interface H {
        void f();
    }
    void g();

//    private interface I {
//
//    }
}

class G {
    private int age;
    public G() {}
    private static class ArrayList{

    }
}