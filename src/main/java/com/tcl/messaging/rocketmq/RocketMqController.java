package com.tcl.messaging.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author li
 * @version 1.0
 * @date 2020/8/7 13:51
 */
@RestController
@RequestMapping("/rocket")
public class RocketMqController {

    @Autowired
    DefaultMQProducer producer;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @GetMapping("/send/{msg}")
    public String sendMessage(@PathVariable String msg) throws Exception {
        Message message = new Message(topic, "tag", msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult result = producer.send(message);
        return result.getSendStatus().name();
    }



}
