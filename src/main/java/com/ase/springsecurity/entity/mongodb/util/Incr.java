package com.ase.springsecurity.entity.mongodb.util;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author gyhstart
 * @create 2020/12/27 - 16:36
 **/
@Document
@Data
public class Incr {
    @Id
    private String id;
    private String collectionName;
    private Long incrId;
}