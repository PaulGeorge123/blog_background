package com.ase.springsecurity.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gyhstart
 * @create 2021/1/1 - 0:04
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdsVo {
    private List<Integer> ids;
    private int num;
}
