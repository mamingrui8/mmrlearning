package com.mmr.learn.theory.thinkinginjava.part11.practice;

import com.github.javafaker.Faker;

import java.util.*;

/**
 * Map扩展到多维
 */
public class MapOfList {
    public static Map<Person, List<? extends Pet>> petPeople =
            new HashMap<>();

    static{
        Locale locale = new Locale("zh", "cn");
        Faker faker = new Faker(locale);
        petPeople.put(new Person("Dawn"), Arrays.asList(new Cymric("Molly"), new Mutt("Spot")));
        petPeople.put(new Person(faker.name().name()), Arrays.asList(new Cymric(faker.name().name()), new Mutt(faker.name().name())));
        petPeople.put(new Person(faker.name().name()), Arrays.asList(new Cymric(faker.name().name()), new Mutt(faker.name().name())));
        petPeople.put(new Person(faker.name().name()), Arrays.asList(new Cymric(faker.name().name()), new Mutt(faker.name().name())));
    }

    public static void main(String[] args) {
        System.out.println("People: " + petPeople.keySet());
        System.out.println("Pets: " + petPeople.values());
        for(Person person : petPeople.keySet()){
            for(Pet p: petPeople.get(person)){
                System.out.println(p);
            }
        }
    }
}
