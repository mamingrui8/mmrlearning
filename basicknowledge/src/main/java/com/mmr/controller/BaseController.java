package com.mmr.controller;

import com.mmr.learn.kafka.demo.ProducerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月29日 10:58
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@Controller
@RequestMapping(value = "/base")
public class BaseController {
    private final ProducerTest producerTest;

    @Autowired
    public BaseController(ProducerTest producerTest) {
        this.producerTest = producerTest;
    }

}
