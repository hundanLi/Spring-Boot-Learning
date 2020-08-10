package com.tcl.messaging.rocketmq;

import lombok.Data;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/7 14:00
 */
@EnableConfigurationProperties({RocketMqConfig.RocketMqProducerProperties.class,
        RocketMqConfig.RocketMqConsumerProperties.class})
@Configuration
public class RocketMqConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqConfig.class);

    @Bean
    @ConditionalOnMissingBean
    public DefaultMQProducer defaultMQProducer() {
        DefaultMQProducer producer = new DefaultMQProducer();

        producer.setNamesrvAddr(producerProperties.getNamesrvAddr());

        producer.setProducerGroup(producerProperties.getGroupName());

        producer.setMaxMessageSize(producerProperties.getMaxMessageSize());

        producer.setSendMsgTimeout(producerProperties.getSendMsgTimeout());

        producer.setRetryTimesWhenSendFailed(producerProperties.getRetryTimesWhenSendFailed());


        try {
            producer.start();
            LOGGER.info("RocketMQ Producer started. Group-name: " + producer.getProducerGroup() + ", Namesrv-Addr: " + producer.getNamesrvAddr());
        } catch (MQClientException e) {
            LOGGER.error("Fail to start RocketMQ Producer: " + e.getErrorMessage());
            throw new RuntimeException(e);
        }

        return producer;
    }


    @Bean
    @ConditionalOnMissingBean
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();

        consumer.setNamesrvAddr(consumerProperties.namesrvAddr);
        consumer.setConsumerGroup(consumerProperties.groupName);
        consumer.setConsumeThreadMin(consumerProperties.consumeThreadMin);
        consumer.setConsumeThreadMax(consumerProperties.consumeThreadMax);

        consumer.setConsumeMessageBatchMaxSize(consumerProperties.consumeMessageBatchMaxSize);

        try {
            consumer.subscribe(consumerProperties.topic, "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt msg : msgs) {
                        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), new String(msg.getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            LOGGER.info("RocketMQ Consumer started. Group-name: " + consumer.getConsumerGroup() + ", Namesrv-Addr: " + consumer.getNamesrvAddr());
        } catch (MQClientException e) {
            LOGGER.error("Fail to start RocketMQ Consumer: " + e.getErrorMessage());
            throw new RuntimeException(e);
        }

        return consumer;
    }


    @Autowired
    RocketMqProducerProperties producerProperties;

    @Autowired
    RocketMqConsumerProperties consumerProperties;

    /**
     * groupName: 发送同一类消息的设置为同一个 group，保证唯一， 默认不需要设置，rocketmq 会使用 ip@pid(pid代表jvm名字) 作为唯一标示。
     * namesrvAddr：Name Server 地址
     * maxMessageSize：消息最大限制，默认 4M
     * sendMsgTimeout：消息发送超时时间，默认 3 秒
     * retryTimesWhenSendFailed：消息发送失败重试次数，默认 2 次
     */
    @Data
    @ConfigurationProperties(prefix = "rocketmq.producer")
    public static class RocketMqProducerProperties{
        private String namesrvAddr = "localhost:9876";

        private String groupName;

        private Integer maxMessageSize = 1024 * 1024 * 4; // 4M

        private Integer sendMsgTimeout = 3000;

        private Integer retryTimesWhenSendFailed = 2;

    }

    @Data
    @ConfigurationProperties(prefix = "rocketmq.consumer")
    public static class RocketMqConsumerProperties{
        private String namesrvAddr = "localhost:9876";

        private String groupName;

        private Integer consumeThreadMin = 20;

        private Integer consumeThreadMax = 20;

        private String topic;

        private Integer consumeMessageBatchMaxSize = 1;

    }
}
