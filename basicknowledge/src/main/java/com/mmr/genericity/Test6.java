package com.mmr.genericity;

import com.mmr.genericity.entity.Pair;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-09-04
 * Time: 20:52
 */
public class Test6 {
    /*
        疑问:
        1. 为什么要给数组设计出"协变性"这么坑人的特性？   (详见: test2)
        2. 既然不能创建泛型类型数组，为何可以申明泛型数组呢？ (详见: test4)
        3. 书本错误 (详见: test5)
     */

    /*
        总结:
        1. 如果需要收集参数化类型对象，只有唯一一种安全且有效的方法:
           使用ArrayList:   ArrayList<Pair<String>>
     */

    /*
       笔记:
        1. 绝对不能用基本类型实例化类型参数。比如Pair<Double>
           为什么不能用？因此类型擦除。
           当类型擦除后，由Pair<T>没有任何extends接口(注意这里我用的是extends),因此擦除后会留下Object类型的域——Pair<Object>
           很可惜，Object不能存储double值。(这里的"存储"指的是"继承"或"连带关系")
        2. 运行时类型查询仅仅适用于原始类型
           如
                1. if(a instanceof Pair<String>)   错误
                2. if(a instanceof Pair<T>)        错误
                3. Pair<String> p = (Pair<String>) a;     WARNING--can only test that is a Pair  只能检测a是一个Pair类型！
                这是一个非常巨大的风险，无论我们在何时使用instanceof(类型测试)，还是强制类型转换时，只要涉及到了泛型类型，都会看到编译器的警告。
                同样的道理，.getClass()永远只会返回原始类型 [绝对不会返回泛型类型！]
                一句话: 所有的查询只产生原始类型，和所附带的泛型类型没有半毛钱关系。 (不信可以看看test1()方法的输出)
     */

    public static void main(String[] args){
        test4();
    }

    public static void test1(){
        Pair<Double> instance = new Pair();
        System.out.println(instance.getClass());
    }

    /**
     * 这个例子重在讲解数组的协变性。
     * 协变性: 在某些情况下，即使某个对象不是数组的基类型，我们也可以把它赋值给数组元素。
     */
    public static void test2(){
        //协变数组举例
        Object[] array = new String[10];
        array[0] = "10";  //注意: 这里"10" 和 10都能通过编译。
        array[1] = 10;
        /*
            情况1:array[0] = 10;
                  现象: 编译通过。但运行报错。
                  分析: 由于10是Integer类型，超类是Object。而Object和String又恰巧是基类和派生类的关系。
                        因此这就是一个典型的数组协变的案例。
                        但作为基类的Object在这里的作用充其量只不过是一个引用类型，真正掌管数组存储元素大权的是String，所以运行时报错java.lang.ArrayStoreException
                  总结:
                        类似
                        class A{ }
                        class B extends A{ }
                        A[] array = new B[10];
                        array[10] = new B();
                        这种才是数组协变真正的用法，但使用A[]无形中"扩大"了数组元素的范围，误导了开发人员。
         */
    }

    public static void test3(){
        B[] t = new A[10];
        t[0] = new B();
    }

    public static void test4(){
        //Pair<String>[] pp = new Pair<String>[10];  //报错
        /*
            让我们一起来讨论上述语句
            此句会报编译错误:Generic array creation.
            我们不能创建一个泛型类型的数组，仅仅因为java不支持而已。
            可这不是脑子有病吗？既然不能创建，为何要允许申明呢？
         */
        Pair<String>[] ppp = new Pair[666]; //通编译器！通过jvm运行！
    }

    /**
     *  如果我今天非要创建一个泛型类型数组，那我们该怎么做
     */
    public static void test5(){
        Pair<String>[] table = (Pair<String>[] )new Pair<?>[6666666]; //其中，?问号代表可变泛型类型

        /*
            Pair<String>[] table = new Pair<String>[10];  //报错
            既然不让直接创建泛型数组，那我们就换一个想法: 创建一个普通数组，数组内装的是泛型对象，最后再把这个普通数组强转成泛型数组。
            <?>仅仅是一个标识，对外申明这是一个泛型类型的数组。和public <T> T test(T a){}  中的<T>有异曲同工之妙。
            当然啦，显而易见这样写是不安全的。
            由于?的缘故，外部被允许传入任意类型的泛型类型。比如我现在传入Pair<Employee>，接着对table[0].getFirst()调用一个String方法，
            将会得到ClassCastException.(具体请看下述语句)

            书本P539的注释错误，Pair<Employee>根本就存不进table中,编译就报错了。
         */
    }
}

class A extends B{

}

class B{

}

class C extends A{

}

