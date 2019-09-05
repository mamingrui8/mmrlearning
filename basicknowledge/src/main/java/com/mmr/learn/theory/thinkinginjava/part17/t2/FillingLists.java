package com.mmr.learn.theory.thinkinginjava.part17.t2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 填充容器
 */
public class FillingLists {
    public static void main(String[] args) {
        List<StringAddress> list = new ArrayList<>(
                /*
                 * Collection.nCopies(int n, Object o)
                 * 返回一个长度为n(复制n份)的List，List不可改变，List内每一个元素中存储的引用都指向o这个对象
                 */
                Collections.nCopies(4, new StringAddress("Hello"))
        );
        System.out.println(list);
        list.get(0).setString("马明瑞");
        System.out.println(list.get(1).getString()); //显然，如果对集合中的某一个引用进行操作，那么其它引用所指向的对象的值也会发生改变。

        /*
            Collections.fill(Collection c, Object o);
            将对象o的引用逐一覆盖(替换)到c集合中的每一个元素上
         */
        list.add(null);
        Collections.fill(list, new StringAddress("World!"));
        System.out.println(list);

        /*
         *  下述代码一定会报错: "java.lang.UnsupportedOperationException"，这是因为Collections.nCopies()返回的CopiesList是一个长度和内容都不可改变的内部类
         *  这里，"内容"指的是"引用指向的地址"，由于Collections.fill()会将集合中每一个引用的地址由原先指向的Mmar，改为指向123，因此会报错。
         *
         *  可能会疑惑为何前面↑不报错呢？这是因为ArrayList的构造器会开辟空间，将传入的集合深度拷贝到新的空间中，因此后续的操作不再受到CopiesList的影响。
         */
        try {
            List<StringAddress> list2 = Collections.nCopies(5, new StringAddress("Mamr"));
            System.out.println(list2);
            Collections.fill(list2, new StringAddress("123"));
            System.out.println(list2);
        }catch (UnsupportedOperationException e){
            e.printStackTrace();
        }
    }
}

class StringAddress {
    private String s;
    public StringAddress(String s) {this.s = s;}

    /**
     * @return 该对象的散列码的无符号十六进制表示(通过hashCode生成)
     */
    public String toString(){return super.toString() + " " + s;}
    public void setString(String s){this.s = s;}
    public String getString(){return s;}
}
