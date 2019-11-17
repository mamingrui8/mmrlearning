package com.mmr.learn.theory.thinkinginjava.part15;

import net.mindview.util.Generator;

import java.util.Iterator;
import java.util.Random;

/**
 * 泛型接口
 * 目标: 实现Generator<Coffee>接口，随机生成不同类型的Coffee对象
 * 使用了"生成器"的思想,它属于工厂方法设计模式的一种应用，只不过生成器创建对象时，不需要任何的参数。
 * 但我认为真正去写代码时，不会使用随机的方式选择类型并获取对象，也就是说，仍然会传递指定的类型参数。
 *
 * @author Charles Wesley
 * @date 2019/11/17 21:45
 */
public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee>{
    //将类型进行了参数化
    private Class[] types = {Latte.class, Mocha.class, Cappuccino.class, Americano.class, Breve.class};
    private static Random rand = new Random(47);
    public CoffeeGenerator() {}
    //For iteration
    private int size = 0;
    public CoffeeGenerator(int size){
        this.size = size;
    }
    @Override
    public Coffee next(){
        try {
            return (Coffee)types[rand.nextInt(types.length)].newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    class CoffeeIterator implements Iterator<Coffee> {
        //size是一个末端哨兵。(Tips: 内部类访问了外部类的属性)
        int count = size;
        @Override
        public boolean hasNext() {
            return count > 0;
        }
        @Override
        public Coffee next() {
            count--;
            //当前的this指向CoffeeIterator，使用CoffeeGenerator.this是为了明确的指出this的来源
            return CoffeeGenerator.this.next();
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Coffee> iterator() {
        return new CoffeeIterator();
    }

    public static void main(String[] args) {
        CoffeeGenerator gen = new CoffeeGenerator();
        for(int i=0; i<5; i++){
            System.out.println(gen.next());
        }
        for(Coffee c:new CoffeeGenerator(5)){
            System.out.println(c);
        }
    }
}

class Coffee{
    private static long counter = 0;
    private final long id = counter++;
    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }
}

class Latte extends Coffee{}
class Mocha extends Coffee{}
class Cappuccino extends Coffee{}
class Americano extends Coffee{}
class Breve extends Coffee {}
