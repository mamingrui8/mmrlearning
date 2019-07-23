package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.AbstractCollection;
import java.util.Iterator;

/**
 * CollectionSequence是一个想使用InterfaceVSIterator中display()方法的类
 */
public class CollectionSequence extends AbstractCollection<Pet> {
    private Pet[] pets;

    @Override
    public Iterator<Pet> iterator() {
        return new Iterator<Pet>(){
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < pets.length;
            }

            @Override
            public Pet next() {
                return pets[index++];
            }


        };
    }

    @Override
    public int size() {
        return pets.length;
    }

    public static void main(String[] args) {
        CollectionSequence sequence = new CollectionSequence();
        InterfaceVSIterator.display(sequence);
        InterfaceVSIterator.display(sequence.iterator());
    }
}
