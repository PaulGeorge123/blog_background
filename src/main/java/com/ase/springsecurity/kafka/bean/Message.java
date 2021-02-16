package com.ase.springsecurity.kafka.bean;

import java.util.Date;

/**
 * @author gyhstart
 * @create 2021/2/10 - 18:07
 **/
public class Message {
    /**
     * id
     */
    private Long id;

    /**
     * 返回码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;

    /**
     * 时间戳
     */
    private Date startTime;

    /**
     * 时间戳
     */
    private Date sendTime;

    /**
     * 日志地址
     */
    private String logPath;

    public Message() {
    }
}