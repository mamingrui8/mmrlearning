package com.mmr.genericity;

import com.mmr.genericity.entity.Pair;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-09-02
 * Time: 17:33
 */
public class Test5 {
    /*
        宗旨:
        1. 虚拟机中没有泛型，只有普通的类和方法。
        2. 所有的类型参数都用它们的限定类型替换。(注意替换规则)
        3. 桥方法被合成来保持多态。
        4. 为保持类型完全性，必要时插入强制类型转换。(没完全弄明白)

        遗留问题:
        以上是《java核心技术》一书所说。
        1. Test3()具体调用并执行时，很奇怪只输出了"多态特性调用的方法"，却没有进入泛型类型擦除后的方法.
           解答: 编译器是在DataInterval类中生成的桥方法，并非是在继承的Pair类当中！！！
                 它会生成public void setSecond(Object second){setSecond((Date) second); } 而这个方法才是真正的用到了多态！
     */

    public static void main(String[] args){
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now().plusDays(1);
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zonedDateTime.toInstant());

        DataInterval interval = new DataInterval();
        Pair<Date> pair = interval;
        pair.setFirst(date);
        pair.setSecond(new Date());
    }
    /*
        1. 虚拟机中不存在泛型类型对象(并非是.class文件中不允许存在)，所有的对象都属于【普通类】。甚至在泛型类型发展的早期，能够将使用泛型的程序编译为在1.0虚拟机上运行的文件。
        2. 无论何时定义一个泛型类型，都会自动提供一个对应的原始类型(raw type)。原始类型的名字就是删去了类型参数之后的泛型类型名。
           擦除(erased)类型变量，并替换为限定类型。(若无限定类型，则使用万能的Object类)
           比如Pair<T>类,因为T是一个无限定的变量，所以直接用Object来替换。
        3. [重大区别]
           1. 在C++中，，C++编译器会使用这些大不相同的类型参数扩充模板，因此List<A>和List<B>生成的C++代码完全不同，即List<A>和List<B>被视作两个不同的类。
               而在Java中，这一现象得到了解决。List<Integer>和List<String>共用了共享相同的类(List<T>)，java编译器仅仅会针对这些传入的类型参数进行擦除和替换。
               由于C++编译器会为每个类型参数扩充一个独一无二的模板，会造成“模板代码膨胀”现象，而java则不存在这些问题。
           2. C++中不能对泛型类型参数加以限制，如果code写错，则代码模板就会报错。而java能够通过extends接口或类的形式灵活的约束传入的泛型类型参数！
        4. 在class文件中，可以出现T，也就是泛型类型。这是因为将.java编译成.class文件并非由jvm来完成，而是由IDE（或其它语言编译器）自己完成。.class文件并非只能从.java而来！
        5.
     */

    /**
     *  案例1: 无限定类型，在擦除类型变量后，使用Object类替换类型变量。
     *  比如 com.mmr.genericity.entity.Pair<T>  此类的原始类型如下所示:
     */
    public static void Test1(){
        class Pair {
            private Object first;
            private Object second;

            public Pair() {
                first = null;
                second = null;
            }

            public Pair(Object first, Object second) {
                this.first = first;
                this.second = second;
            }

            public Object getFirst() {
                return this.first;
            }

            public Object getSecond() {
                return this.second;
            }

            public void setFirst(Object newValue) {
                this.first = newValue;
            }

            public void setSecond(Object newValue) {
                this.second = newValue;
            }
        }
    }

    /**
     * 案例2: 翻译泛型表达式
     * 举例: pair.<Employee>getFirst()
     */
    public static void Test2(){
        Pair<Employee> pair = new Pair<>();
        //注意下面这句话，原本调用泛型方法时，需要传入泛型类型参数
        Employee employee1 = pair.<Employee>getFirst();
        //擦除getFirst()方法的返回类型后，原本是将返回Object类型对象，但此处编译器会自动插入Employee类并为返回值进行强制类型转换
        //也就是说，pair.getFirst()这句话在jvm虚拟机中被解释成了以下两句话:
        //  1. 执行pair的原始方法getFirst()
        //  2. 将返回的Object类型对象强制类型转化为Employee对象
        //总结一下，只要在Pair<Employee>，这个最初的地方赋予了受限制的参数类型，那么，无论是Pair的泛型方法还是泛型域，都会受到来自Employee类型的强制
        //类型转换！
        Employee employee = pair.getFirst();
    }

    /**
     *  案例3: 翻译泛型方法
     */
    public static void Test3(){
        /*
            首先，让我们阅读一个泛型方法: public static <T extends Comparable> T min(T[] a)
            接着，擦除这个泛型方法（只留下限定方法）: public static Comparable min(Comparable[] a)
            那么现在问题来了: 请看DataInterval
            DataInterval被擦除后变成如下样貌:
            class DataInterval extends Pair{
                public void setSecond(Date secondData){
                    if(secondData.compareTo(getFirst()) > 0){
                        super.setSecond(secondData);
                    }
                }
            }

            不难发现，setSecond(Data secondData)其实是子类(DataInterval)对超类(Pair)setSecond(Object secondData)的一个重写/覆盖。
            因此当使用如下语句进行执行时:
         */
        DataInterval interval = new DataInterval();
        Pair<Date> pair = interval;
        pair.setSecond(new Date());

        /*
            但这里有个非常奇特的方法: public void setSecond(Object second) ,它是从被擦除后的Pair类继承而来。
            所以这里就矛盾了，
            一边是对调用setSecond()使用多态的特性，父类引用调用子类方法，
            一边是对调用setSecond()使用从父类Pair继承而来的方法。
            那到底调用的哪个方法呢？类型擦除与多态发生了冲突。

            这里，java的设计者希望对setSecond()的调用具有多态性，并调用最适合的那个方法。为了解决这个问题，就需要编译器在DateInterval类
            中生成一个【桥方法】(bridge method)

            因此上述调用在jvm中被编译成了如下语句语序
            public void setSecond(Object second){
                setSecond((Date)second);
            }
            先调用擦除后继承而来的方法，然后调用多态方法。
         */
    }

    /**
     *  利用泛型调用遗留代码
     */
    public static void Test4(){
        /*
            什么是遗留代码?
         */
    }
}

class Employee { }

/**
 *  DataInterval 用于描述一段时间区间 (FirstData,SecondData) ，规定FirstData必须小于SecondData
 */
class DataInterval extends Pair<Date>{
    public void setSecond(Date secondData){
        System.out.println("多态特性调用的方法");
        if(secondData.compareTo(getFirst()) > 0){
            super.setSecond(secondData);
        }
    }
}

