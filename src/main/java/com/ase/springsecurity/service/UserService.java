package com.ase.springsecurity.service;

import com.ase.springsecurity.entity.User;
import com.ase.springsecurity.entity.vo.ConditionsVo;
import com.ase.springsecurity.result.Result;

import java.util.List;

/**
 * @author gyhstart
 * @create 2020/11/27 - 23:49
 **/
public interface UserService {

    /**
     * MySQL登录
     *
     * @return
     */
    List<User> login(String nickname, String password);

    /**
     * MySQL user 增加
     *
     * @param user
     * @return
     */
    Result saveUser(User user);

    /**
     * MySQL user 删除
     *
     * @param id
     */
    Result removeUser(int id);

    /**
     * MySQL user 修改
     *
     * @param user
     */
    Result modifyUser(User user);

    /**
     * MySQL user 查询所有
     *
     * @return
     */
    List<User> queryAllUser();

    /**
     * MySQL 与原始密码比较
     *
     * @param oldPassword
     */
    Result oldPasswordToCompare(String oldPassword, String nickName);

    /**
     * MySQL 修改密码
     *
     * @param newPassword
     */
    Result ModifyPasswordToCompare(String newPassword, String nickName);

    /**
     * MySQL 根据id查询用户
     *
     * @return
     */
    Result queryUserById(int id);

    /**
     * MySQL 模糊查询用户
     *
     * @return
     */
    List<User> queryUserByFuzzy(String username);

    /**
     * MySQL 批量删除
     *
     * @param ids
     */
    Result removeUserList(List<Integer> ids, int num);

    /**
     * MySQL 条件查询用户
     *
     * @return
     */
    List<User> queryUserByConditions(ConditionsVo conditionsVo);

    /**
     * MySQL 查询用户数
     *
     * @return
     */
    Result queryUserCount();
}
