package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.Collection;
import java.util.Iterator;

public class InterfaceVSIterator {
    public static void display(Iterator<? extends Pet> it){
        while(it.hasNext()){
            Pet pet = it.next();
            System.out.print(pet.id() + ":" + pet + " ");
        }
        System.out.println();
    }

    public static void display(Collection<? extends Pet> collection){
        for(Pet pet : collection)
            System.out.print(pet.id() + ":" + pet + " ");
        System.out.println();
    }
}
