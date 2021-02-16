package com.ase.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author gyhstart
 * @date 2021/2/11
 * @email 15218979950@163.com
 * @description WebSocketConfig配置类，注入对象ServerEndpointExporter，
 * *            这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
