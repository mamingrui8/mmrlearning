package com.mmr.learn.theory.thinkinginjava.part17.t2;

import java.util.ArrayList;
import java.util.HashSet;
import net.mindview.util.*;

public class CollectionDataGeneration {
    public static void main(String[] args) {
        System.out.println(new ArrayList<String>(
                CollectionData.list(new RandomGenerator.String(9), 10)
        ));

        System.out.println(new HashSet<Integer>(
                new CollectionData<Integer>(new RandomGenerator.Integer(), 10)
        ));
    }
}
