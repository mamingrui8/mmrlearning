package com.mmr.learn.theory.thinkinginjava;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;

/**
 * @author mamr
 * @date 2020/1/31 7:07 下午
 */
public class Test {
    public static void main(String[] args) {
        t(null);
    }

    public static void t(@Nullable Test tt) {
        System.out.println(123);
    }
}
