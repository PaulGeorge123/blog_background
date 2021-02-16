package com.ase.springsecurity.controller;

import com.ase.springsecurity.kafka.bean.KafkaMessage;
import com.ase.springsecurity.kafka.producer.KafkaProducer;
import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.result.ResultCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gyhstart
 * @create 2021/2/10 - 18:06
 **/
@Log4j2
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducer producer;

    @PostMapping("/send")
    public Result sendKafka(@RequestBody KafkaMessage message) {
        try {
            log.info("kafka的消息={}", message);
            producer.send(message);
            log.info("发送kafka成功.");
            return Result.success(message);
        } catch (Exception e) {
            log.error("发送kafka失败", e);
            return Result.failure(ResultCode.valueOf("发送kafka失败"),e);
        }
    }
}
