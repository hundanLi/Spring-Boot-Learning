package com.tcl.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/7 10:47
 */
@Service
public class KafkaService {
    @Autowired
    KafkaTemplate kafkaTemplate;

    String topic = "test";


    public void sendMessage(String msg) {
        kafkaTemplate.send(topic, msg);
    }

    @KafkaListener(topics = "test")
    public void receiveMessage(String content) {
        System.out.println("Kafka receiving msg: " + content);

    }

}
