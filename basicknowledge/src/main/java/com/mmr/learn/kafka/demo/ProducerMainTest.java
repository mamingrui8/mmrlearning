package com.mmr.learn.kafka.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ProducerMainTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});

        ProducerTest producerTest = context.getBean(ProducerTest.class);
        producerTest.sendMessage();

//        ProducerTest producer = SpringUtil.getBean(ProducerTest.class);
//        producer.sendMessage();
//        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
//        applicationContext.getBean(ProducerTest.class).sendMessage();
    }


}

