package com.mmr.learn.javafaker;

import com.github.javafaker.Company;
import com.github.javafaker.Faker;

/**
 * Description:
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月14日 21:01
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class FakerTest {
    public static void main(String[] args) {
        Faker faker = new Faker();

        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String firstName = faker.name().firstName(); // Emory
        String lastName = faker.name().lastName(); // Barton

        String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449

        Company company = faker.company();

        System.out.println(streetAddress);
    }
}
