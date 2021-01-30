package com.ase.springsecurity.entity.mongodb;

import com.ase.springsecurity.entity.mongodb.util.AutoInc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author gyhstart
 * @create 2020/12/27 - 22:20
 **/

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) //实现链式编程
public class Blog {
    @Id
    @AutoInc
    private long id;
    private String title; //标题
    private String content; //内容
    private String author; //作者
    private Date datetime; //日期
    private String category; //类别
    private int thumb_up; //点赞
    private int collection; //收藏
    private int attention; //关注
    private int browse; //浏览
}
