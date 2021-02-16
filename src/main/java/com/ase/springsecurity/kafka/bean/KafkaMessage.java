package com.ase.springsecurity.kafka.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gyhstart
 * @create 2021/2/10 - 20:41
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {
    private String msg;
    private String user;
}
