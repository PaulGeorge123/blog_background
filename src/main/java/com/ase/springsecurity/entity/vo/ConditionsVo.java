package com.ase.springsecurity.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gyhstart
 * @create 2021/1/1 - 12:13
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionsVo {
    private String role;
    private int gender;
    private int logicRemove;

}
