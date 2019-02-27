package com.mmr;

import com.mmr.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SpringUtil.class)
@EnableConfigurationProperties
public class BasicKnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicKnowledgeApplication.class, args);
    }

}

