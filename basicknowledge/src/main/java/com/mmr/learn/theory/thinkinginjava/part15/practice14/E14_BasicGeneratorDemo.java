package com.mmr.learn.theory.thinkinginjava.part15.practice14;

import com.mmr.learn.theory.thinkinginjava.part15.BasicGenerator;
import com.mmr.learn.theory.thinkinginjava.part15.CountedObject;
import net.mindview.util.Generator;

/**
 * 修改BasicGeneratorDemo类，使其显示的构建generator(也就是不使用create()方法，而是使用显示的构造器)
 * @author Charles Wesley
 * @date 2019/11/24 17:33
 */
public class E14_BasicGeneratorDemo {
    public static void main(String[] args) {
        Generator<CountedObject> gen = new BasicGenerator<CountedObject>(CountedObject.class);
        for(int i=0; i<4; i++){
            System.out.println(gen.next());
        }
    }
}
