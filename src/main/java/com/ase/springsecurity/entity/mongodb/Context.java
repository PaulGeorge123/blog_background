package com.ase.springsecurity.entity.mongodb;

import com.ase.springsecurity.entity.mongodb.util.AutoInc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author gyhstart
 * @create 2021/1/24 - 16:46
 **/
@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) //实现链式编程
public class Context {
    @Id
    @AutoInc
    private long id;
    private String content; //标题
    private String title; //内容
    private String username; //类别
}