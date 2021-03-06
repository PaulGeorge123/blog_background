package com.ase.springsecurity;

import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author Curry
 */
@Log4j2
@SpringBootApplication
@MapperScan("com.ase.springsecurity.mapper")
//exclude表示自动配置时不包括Multipart配置
//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class SpringsecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityApplication.class, args);

        log.info("========================= 启动完毕 =========================");
        // log.info("http://localhost:8090/swagger-ui.html");
    }

    /**
     * 显示声明CommonsMultipartResolver为mutipartResolver
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        //resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(40960);
        resolver.setMaxUploadSize(10 * 1024 * 1024);//上传文件大小 3M 3*1024*1024
        return resolver;
    }
}
