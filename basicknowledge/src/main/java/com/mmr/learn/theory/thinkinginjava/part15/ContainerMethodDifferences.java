package com.mmr.learn.theory.thinkinginjava.part15;

import com.mmr.learn.theory.thinkinginjava.part5.practice17.Main;

import java.lang.reflect.Method;
import java.util.*;

/**
 * [重要] 使用Sets.difference()打印出java.util包中各种Collection类与Map类之间的方法差异
 * @author Charles Wesley
 * @date 2019/11/24 19:20
 */
public class ContainerMethodDifferences {
    static Set<String> methodSet(Class<?> type) {
        Set<String> result = new TreeSet<>();
        for(Method m : type.getMethods()){
            result.add(m.getName());
        }
        return result;
    }

    static void interfaces(Class<?> type){
        System.out.print("Interfaces in " + type.getSimpleName());
        List<String> result = new ArrayList<>();
        for(Class<?> c : type.getInterfaces()){
            result.add(c.getSimpleName());
        }
        System.out.println(result);
    }

    static  Set<String> object = methodSet(Object.class);

    static {object.add("clone");}

    static void difference(Class<?> superset, Class<?> subset){
        System.out.println(superset.getSimpleName() + " extends " + subset.getSimpleName() + ", adds: ");
        Set<String> comp = Sets.difference(methodSet(superset), methodSet(subset));
        comp.removeAll(object); //don't show 'Object' methods
        System.out.println(comp);
        interfaces(superset);
    }

    public static void main(String[] args) {
        System.out.println("Collection: " + methodSet(Collection.class));
        interfaces(Collection.class);
        difference(Set.class, Collection.class);
        difference(HashSet.class, Set.class);
        difference(LinkedHashSet.class, HashSet.class);
        difference(TreeSet.class, Set.class);
        difference(List.class, Collection.class);
        difference(ArrayList.class, List.class);
        difference(LinkedList.class, List.class);
        difference(Queue.class, Collection.class);
        difference(PriorityQueue.class, Queue.class);

        System.out.println("===========================================================================");
        System.out.println("Map: " + methodSet(Map.class));
        difference(HashMap.class, Map.class);
        difference(LinkedHashMap.class, HashMap.class);
        difference(SortedMap.class, Map.class);
        difference(TreeMap.class, Map.class);
    }
}
