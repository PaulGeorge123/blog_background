package com.ase.springsecurity.entity.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author gyhstart
 * @create 2021/2/16 - 19:27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor

///头背景设置成红色 IndexedColors.RED.getIndex()
//@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 10)
//头字体设置成20
@HeadFontStyle(fontHeightInPoints = 10)
////内容的背景设置成绿色 IndexedColors.GREEN.getIndex()
//@ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 17)
////内容字体设置成20
//@ContentFontStyle(fontHeightInPoints = 20)
public class DownloadUserData {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("用户号")
    private String nickname;

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("密码")
    private String password;

    @ExcelProperty("权限")
    private String role;

    @ExcelProperty("注册日期")
    private Date datetimes;

    @ExcelProperty("性别")
    private Integer gender;

    @ExcelProperty("是否注销")
    private Integer logicRemove;

    public DownloadUserData(String nickname, String username, String role, Date datetimes, Integer gender) {
        this.nickname = nickname;
        this.username = username;
        this.role = role;
        this.datetimes = datetimes;
        this.gender = gender;
    }
}
