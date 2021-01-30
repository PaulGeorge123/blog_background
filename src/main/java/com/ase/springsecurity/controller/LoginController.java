package com.ase.springsecurity.controller;

import com.ase.springsecurity.entity.User;
import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.result.ResultCode;
import com.ase.springsecurity.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gyhstart
 * @create 2020/11/27 - 21:51
 **/
@Log4j2
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@Param("nickname") String nickname, @Param("password") String password) {
        List<User> userList = userService.login(nickname, password);
        if (userList.size() > 0){
            Map<String,Object> map = new HashMap<>();
            map.put("username",userList.get(0).getUsername());
            map.put("nickname",userList.get(0).getNickname());
            map.put("role",userList.get(0).getRole());
            map.put("id",userList.get(0).getId());
            return Result.success(map);
        }
        return Result.failure(ResultCode.SERVER_HAS_COLLAPSE);
    }

    /**
     * MySQL 与原始密码比较
     * @param oldPassword
     */
    @PostMapping("/oldPasswordToCompare")
    public Result oldPasswordToCompare(@RequestParam("oldPassword") String oldPassword,@RequestParam("nickName") String nickName){
        return userService.oldPasswordToCompare(oldPassword,nickName);
    }

    /**
     * MySQL 修改密码
     * @param newPassword
     */
    @PostMapping("/modifyPassword")
    public Result ModifyPassword(@RequestParam("newPassword") String newPassword,@RequestParam("nickName") String nickName){
        return userService.ModifyPasswordToCompare(newPassword,nickName);
    }
}
