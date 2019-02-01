package com.mmr.learn.annotation.CustomAnnotation.lesson1;

/**
 * Description: 使用注解为对象属性赋值
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月29日 10:18
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class User {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    @Init(value = "MaMingRui")
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    @Init(valueAge = 26)
    public void setAge(Integer age) {
        this.age = age;
    }
}
