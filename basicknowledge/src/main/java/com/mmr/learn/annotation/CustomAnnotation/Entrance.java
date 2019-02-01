package com.mmr.learn.annotation.CustomAnnotation;

import com.mmr.learn.annotation.CustomAnnotation.lesson1.User;
import com.mmr.learn.annotation.CustomAnnotation.lesson1.UserFactory;
import org.junit.Test;

/**
 * Description: 自定义注解
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月29日 10:05
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Entrance {
    /**
     * 疑问:
     * 1. 注解被继承有什么作用？能够继承哪些内容？
     */

    @Test
    public void test1(){
        User user = UserFactory.generateUser();
        assert user!=null;

        System.out.println("name->"  + user.getName());
        System.out.println("age->" + user.getAge());
    }
}
