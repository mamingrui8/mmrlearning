package com.mmr.learn.thread.lesson.lesson12.innerStaticClass;
import com.mmr.learn.thread.lesson.lesson12.innerStaticClass.PublicClass.PrivateClass;

/**
 * Description: 静态内置类
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 17:01
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
       PrivateClass privateClass = new PrivateClass();
       privateClass.setAge("ageValue");
       privateClass.setAddress("addressValue");
       System.out.println(privateClass.getAge() + " " + privateClass.getAddress());
   }
}
