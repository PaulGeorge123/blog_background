package com.ase.springsecurity.entity.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author gyhstart
 * @create 2021/2/16 - 20:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadUserData {
    private String nickname;
    private String username;
    private String role;
    private Date datetimes;
    private Integer gender;
}