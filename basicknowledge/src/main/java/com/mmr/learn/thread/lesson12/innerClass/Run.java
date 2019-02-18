package com.mmr.learn.thread.lesson12.innerClass;
import com.mmr.learn.thread.lesson12.innerClass.PublicClass.PrivateClass;

/**
 * 内置类
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 16:45
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class Run {
    public static void main(String[] args){
        PublicClass publicClass = new PublicClass();
        publicClass.setUsername("usernameValue");
        publicClass.setPassword("passwordValue");
        System.out.println(publicClass.getUsername() +  " " + publicClass.getPassword());
        PrivateClass privateClass = publicClass.new PrivateClass();//此处初始化方式较为特殊
        privateClass.setAge("ageValue");
        privateClass.setAddress("addressValue");
        System.out.println(privateClass.getAge() + " " + privateClass.getAddress());
    }
}
