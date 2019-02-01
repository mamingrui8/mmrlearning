package com.mmr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@ImportResource({"classpath:disconf.xml"})
@EnableConfigurationProperties
public class BasicKnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicKnowledgeApplication.class, args);
    }

}

