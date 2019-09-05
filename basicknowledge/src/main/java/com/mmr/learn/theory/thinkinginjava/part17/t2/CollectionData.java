package com.mmr.learn.theory.thinkinginjava.part17.t2;

import com.mmr.learn.theory.source.java.util.ArrayList;
import net.mindview.util.Generator;

/**
 * 一种Generator解决方案
 *
 * 通过传入Collection来创建新的Collection
 */
public class CollectionData<T> extends ArrayList<T> {
    public CollectionData(Generator<T> gen, int quantity){
        for(int i=0; i<quantity; i++){
            add(gen.next());
        }
    }

    public static <T> CollectionData<T> list(Generator<T> gen, int quantity){
        return new CollectionData<T>(gen, quantity);
    }
}
