package com.mmr.learn.theory.thinkinginjava.part15;

import net.mindview.util.Generator;

/**
 * 为CountedObject创建一个Generator生成器
 * @author Charles Wesley
 * @date 2019/11/24 17:29
 */
public class BasicGeneratorDemo {
    public static void main(String[] args) {
        Generator<CountedObject> gen = BasicGenerator.create(CountedObject.class);
        for(int i=0; i<5; i++){
            System.out.println(gen.next());
        }
    }
}
