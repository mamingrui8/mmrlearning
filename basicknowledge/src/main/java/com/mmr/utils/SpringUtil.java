package com.mmr.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月11日 10:58
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null){
            SpringUtil.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext(){
        return SpringUtil.applicationContext;
    }

    //通过name获取Bean
    public static Object getBean(String name){
        return SpringUtil.getApplicationContext().getBean(name);
    }

    //通过class获取Bean
    public static<T> T getBean(Class<T> clazz){
        return SpringUtil.getApplicationContext().getBean(clazz);
    }

    //通过name和class联合获取Bean
    public static<T> T getBean(String name, Class<T> clazz){
        return SpringUtil.getApplicationContext().getBean(name, clazz);
    }
}
