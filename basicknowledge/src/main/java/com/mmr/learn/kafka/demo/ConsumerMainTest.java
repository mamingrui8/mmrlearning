package com.mmr.learn.kafka.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author hzwanghuiqi
 * @version 2016/09/26
 */
@Component
public class ConsumerMainTest {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        ConsumerTest consumerTest = context.getBean(ConsumerTest.class);
        // consumerTest.simpleConsumer();
        // consumerTest.consumer();
        consumerTest.consumerExactly();


//        ConsumerTest consumer = SpringUtil.getBean(ConsumerTest.class);
//        consumer.consumerExactly();
    }

}
