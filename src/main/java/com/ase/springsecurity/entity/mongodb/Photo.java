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
 * @create 2021/1/3 - 22:34
 **/

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) //实现链式编程
public class Photo {
    @Id
    @AutoInc
    private long id;
    private String username; //用户
    private String path; //图片地址
    private String fileSize; //图片大小
    private String newFileName; //图片名(新)
    private String oldFileName; //图片名(原)
}
