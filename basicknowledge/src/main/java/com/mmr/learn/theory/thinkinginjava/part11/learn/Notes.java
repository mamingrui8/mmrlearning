package com.mmr.learn.theory.thinkinginjava.part11.learn;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.*;

/**
 * 笔记
 */
public class Notes {
    /**
     * Arrays.asList() 不能调整尺寸
     */
    @Test
    public void demo1(){
        //由Arrays.asList()转换而来的集合无法使用add()或remove()方法修改集合的长度
        //因为其底层表示仍然是数组，因此不能调整尺寸
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);

        //以下语句报错: java.lang.UnsupportedOperationException
        integers.add(5);
        integers.remove(1);
    }

    /**
     * Collections.addAll()与Collection.addAll()方法的区别
     */
    @Test
    public void demo2(){
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        Collection<Integer> collection = new ArrayList<>(integers);
        //虽然integers不能改变长度，但collection可以
        //这是因为new ArrayList()的底层是由elementData(数组类型)来存储数据，新增数据时会调用
        //Arrays.copyOf(Object[] original, int length, Class<?> c) 赋值给elementData覆盖其原有数据。
        collection.add(5);
        collection.forEach(System.out::println);
    }

    @Test
    public void demo3(){
        List<String> strings = Arrays.asList("1", "2");
        strings.add("3");
    }

    /**
     * Collections.shuffle() 打乱集合中元素的排列顺序
     */
    @Test
    public void demo4(){
        List<String> strings = new ArrayList<>(Arrays.asList("1", "2", "3"));
        System.out.println(strings);
        Random random = new Random(47);
        Collections.shuffle(strings, random);
        System.out.println(strings);
    }

    /**
     * Collections.retainAll() 取交集
     */
    @Test
    public void demo5(){
//        List<String> strings1 = Arrays.asList("1", "3", "4");
//        List<String> strings2 = Arrays.asList("2", "3", "5");
//        boolean flag = strings1.retainAll(strings2);
//        System.out.println("本次取交集是否成功: " + flag);
//        System.out.println(strings1);
//        System.out.println(strings2);

        //上面的代码之所以会报错，是因为Arrays.asList()获取的集合底层是数组，数组的长度不可变，因此第65行报错

        List<String> strings1 = new ArrayList<String>(){{add("1");add("3");add("4");}};
        List<String> strings2 = new ArrayList<String>(){{add("2");add("3");add("5");}};
        boolean flag = strings1.retainAll(strings2);
        System.out.println("本次取交集是否成功: " + flag);
        System.out.println(strings1);
        System.out.println(strings2);
    }

    /**
     * 迭代器
     */
    @Test
    public void demo6(){
        @Getter
        @Setter
        class Pet{
            private int id;
            private String name;
            public String toString(){return name;}
            public boolean equals(Pet pet){return pet.getId() == this.getId();}
        }

        Locale locale = new Locale("zh", "cn");
        Faker faker = new Faker(locale);
        List<Pet> pets = new ArrayList<>();
        for(int i =0; i< 12; i++){
            Pet pet = new Pet();
            pet.setId(i);
            pet.setName(faker.name().name());
            pets.add(i, pet);
        }

        Iterator<Pet> it = pets.iterator();
        while(it.hasNext()){
            Pet p = it.next();
            System.out.print(p.getId() + ":" + p + " ");
        }
        System.out.println();
        for(Pet p: pets){
            System.out.print(p.getId() + ":" + p + " ");
        }
        System.out.println();
        it = pets.iterator();
        for(int i=0; i< 6; i++){
            it.next(); //取出一条记录
            it.remove(); //删除一条记录
        }
        System.out.print(pets);
    }

    @Test
    public void demo7(){
        System.out.println(Integer.valueOf('+'));
        System.out.println(Integer.valueOf('-'));
    }
}
