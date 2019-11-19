package com.mmr.learn.theory.thinkinginjava.part15.practice8;

import net.mindview.util.Generator;

import java.util.Iterator;
import java.util.Random;

/**
 * 电影人物生成器
 * 目标: 无需传入任何参数，只要调用生成器，就能创建出电影人物
 * @author Charles Wesley
 * @date 2019/11/19 23:31
 */
public class StoryGenerator implements Generator<StoryCharacters>, Iterable<StoryCharacters> {
    //这里不能写泛型，因为java不支持泛型数组
    private Class[] characters = {Captain.class, Thor.class, BlackMan.class, Spider.class};
    //伪随机器  只要我指定了种子，无论随机多少次，其结果都是相同的
    private Random rand = new Random(47);
    //用于iterator 记录了本次需要循环迭代的次数
    private int size;

    public StoryGenerator(int count){
        size = count;
    }

    @Override
    public StoryCharacters next() {
        try {
            return (StoryCharacters)characters[rand.nextInt(characters.length)].newInstance();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    class StoryIterator implements Iterator<StoryCharacters>{
        private int count = size;

        @Override
        public boolean hasNext() {
            return count>0;
        }

        @Override
        public StoryCharacters next() {
            count--;
            return StoryGenerator.this.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<StoryCharacters> iterator() {
        return new StoryIterator();
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        for(StoryCharacters character : new StoryGenerator(3)){
            System.out.println(character);
        }
    }
}

class StoryCharacters{
    private static long counter;
    private final long id = counter++;
    @Override
    public String toString(){
        return this.getClass().getSimpleName() + " " + id;
    }
}
class GoodGuys extends StoryCharacters{
    @Override
    public String toString(){
        return super.toString() + " " + "is a good guy";
    }
}
class BadGuys extends StoryCharacters{
    @Override
    public String toString(){
        return super.toString() + " " + "is a bad guy";
    }
}
class Captain extends GoodGuys{}
class Thor extends GoodGuys{}
class BlackMan extends BadGuys{}
class Spider extends BadGuys{}