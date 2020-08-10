package com.tcl.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/7 11:04
 */
@RestController
public class KafkaController {

    @Autowired
    KafkaService kafkaService;

    @GetMapping("/kafka/{msg}")
    public String sendMsg(@PathVariable String msg) {
        kafkaService.sendMessage(msg);
        return "OK";
    }
}
