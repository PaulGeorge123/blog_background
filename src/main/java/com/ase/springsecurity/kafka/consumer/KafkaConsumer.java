package com.ase.springsecurity.kafka.consumer;

import com.alibaba.fastjson.JSONObject;
import com.ase.springsecurity.entity.User;
import com.ase.springsecurity.kafka.constant.TopicConst;
import com.ase.springsecurity.websocket.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gyhstart
 * @create 2021/2/10 - 17:40
 **/
@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    private WebSocketService webSocketService;

    @KafkaListener(topics = TopicConst.EXECUTOR_TOPIC, groupId = TopicConst.TOPIC_GROUP1)
    public void consumerTopic(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("topic_test 消费了： Topic:" + topic + ",Message:" + msg);
            ack.acknowledge();
        }
    }
}
