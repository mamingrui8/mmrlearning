package com.mmr.genericity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-09-05
 * Time: 20:54
 */
public class Test7 {

    //https://www.cnblogs.com/uptownBoy/articles/1698335.html 目前看到了第7点
    //java核心技术目前看到了P540
    /*
        疑问:
     */

    /*
        总结:
     */

    /*
        猜想:
        1. 在将来，会不会出现可变长泛型类型参数?
           答: 由于J2SE1.5中，泛型机制存在一个内在约束: 不能拿“用标识符来表达的类型”创建这一标识符的实例。除非后期java做相应改进，否则对于这个
               问题，没有太好的解决办法。

     */

    /*
        笔记:
        1. Varargs可变长参数产生的缘由 test1(总)
        2. Varargs如何转发
     */


    /**
     * 参数个数可变的参数
     * 重点关注:
     * 1. 写法
     * 2. 如何读取参数传入的值
     *
     * 设计缘由:
     *    在J2SE1.4以前，java不支持Varargs可变长参数，实参个数和形参个数必须一一对应。换句话说，在方法定义时，方法的个数、类型已经完全被固定下来了。
     *    尽管可以通过重载机制，为同一个方法提供带有不同数量、不同类型的形参的版本。但这仍然不能达到"让实参数量任意变化"的目的。
     *    可能我们会想到main()方法，main方法不就可以传入非固定个数和类型的参数吗？是的，main()方法的形参是数组，利用了数组能够装载多个参数的特性。
     *    为了使调用形式更加灵活、简便，J2SE1.4设计出了Varargs可变长参数。
     *    注意: 并没有克服数组装载参数类型固定的缺陷，此处不禁让人想象，以后会不会有可变长泛型类型参数？
     */
    public static void test1(String name, Integer age, int... values){
        /*
           1. Varargs参数允许和其它定长参数组合，但只能被放在末尾，有且仅有一个。
           2. 让我们来看看编译器到底对Varargs做了什么，请看Test7.class
              实际上，编译器仍然将Varargs转成了数组 int[] value,正式由于这种暗地的转换，导致我们不能使用如下方法签名进行方法重载
              public static void test1(String name,Integer age, int[] value){}
              实际上 test1("马明瑞", 24, 1,2,3,4)  相当于test1("马明瑞", 24, new int[]{1,2,3,4}) 由于数组允许为空，因此Varargs参数不传也没关系
           3. 使用如下方式进行接收:
         */
        for(int value: values){
            System.out.println("value");
        }
    }


    /**
     * 重点看: test2()如何转发Varags至test3()
     * @param values
     */
    public static void test2(String... values){
        test3("test2()调用test3()", values);
    }

    public static void test3(String message, String... arguments){ //其实就是在传递一个String[] 数组而已
        for(String f: arguments){
            System.out.println(f);
        }
    }

    public static <T> void test4(T[] args){

    }

    static public void main(String[] args){
        test2("马明瑞", "24岁", "身在北京");
    }
}
