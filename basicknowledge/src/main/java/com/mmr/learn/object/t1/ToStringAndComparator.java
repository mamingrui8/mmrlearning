package com.mmr.learn.object.t1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * 可以用以下方法代替传统的toString()和compareTo()
 * 感觉这个方法的适用面太窄了
 */
public class ToStringAndComparator {
    public static void main(String[] args) {
        Person p = new Person("小马", 25);
        final Set people = new HashSet();
        people.add(p);
        people.stream().map(Person.TO_STRING).forEachOrdered(System.out::print);  //这里利用map()，在遍历对象集合时，把每一个对象转成了字符串，最后打印出来而已
    }
}

@AllArgsConstructor
@Getter
@Setter
class Person{
    private String name;
    private Integer age;

    public final static Function<Person, String> TO_STRING = p -> p.getName() + " : " + p.getAge();

    public final static Comparator<Person> COMPARATOR = Comparator.comparing(Person::getAge).thenComparing(Person::getName);
}
