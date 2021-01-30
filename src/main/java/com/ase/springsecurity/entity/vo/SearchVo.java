package com.ase.springsecurity.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gyhstart
 * @create 2021/1/28 - 0:10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchVo {
    private String title;
    private String category;
    private String author;
}
