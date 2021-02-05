package com.ase.springsecurity.service.impl;

import com.ase.springsecurity.entity.User;
import com.ase.springsecurity.entity.UserExample;
import com.ase.springsecurity.entity.vo.ConditionsVo;
import com.ase.springsecurity.mapper.UserMapper;
import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.result.ResultCode;
import com.ase.springsecurity.service.UserService;
import com.ase.springsecurity.utils.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author gyhstart
 * @create 2020/11/27 - 23:50
 **/
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * MySQL user 登录
     *
     * @param nickname
     * @param password
     * @return
     */
    @Override
    public List<User> login(String nickname, String password) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNicknameEqualTo(nickname);
        criteria.andPasswordEqualTo(password);
        return userMapper.selectByExample(userExample);
    }

//    @Override
//    public List<User> login(String nickname, String password) {
//        if (redisUtil.lGetListSize(nickname) > 0){
//            return (List<User>) redisUtil.lGet(nickname,0,0).get(0);
//        }
//        UserExample userExample = new UserExample();
//        UserExample.Criteria criteria = userExample.createCriteria();
//        criteria.andNicknameEqualTo(nickname);
//        criteria.andPasswordEqualTo(password);
//        List<User> userList = userMapper.selectByExample(userExample);
//        redisUtil.lSet(nickname, userList);
//        return (List<User>) redisUtil.lGet(nickname,0,0).get(0);
//    }

    /**
     * MySQL user 增加
     *
     * @param user
     * @return
     */
    @Override
    public Result saveUser(User user) {
        user.setNickname(user.getNickname());
        user.setUsername(user.getUsername());
        user.setRole(user.getRole());
        user.setGender(user.getGender());
        user.setPassword(user.getPassword());
        user.setLogicRemove(1);
        user.setDatetimes(new Date());
        int insert = userMapper.insert(user);
        if (insert == 1) {
            return Result.success(1);
        }
        return Result.success(0);
    }

    /**
     * MySQL user 删除
     *
     * @param id
     */
    @Override
    public Result removeUser(int id) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        User user = new User();
        user.setLogicRemove(0);
        int remove = userMapper.updateByExampleSelective(user, example);
        if (remove == 1) {
            return Result.success(1);
        }
        return Result.success(0);
    }

    /**
     * MySQL user 修改
     *
     * @param user
     */
    @Override
    public Result modifyUser(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(user.getId());
        User userMap = new User();
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            userMap.setUsername(user.getUsername());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            userMap.setPassword(user.getPassword());
        }
        if (user.getRole() != null && !user.getRole().isEmpty()) {
            userMap.setRole(user.getRole());
        }
        if (user.getGender() != null && !String.valueOf(user.getGender()).equals("")) {
            userMap.setGender(user.getGender());
        }
        if (user.getLogicRemove() != null && !String.valueOf(user.getLogicRemove()).equals("")) {
            userMap.setLogicRemove(user.getLogicRemove());
        }
        if (user.getNickname() != null && !user.getNickname().isEmpty()) {
            userMap.setNickname(user.getNickname());
        }
        userMap.setDatetimes(new Date());
        int userResult = userMapper.updateByExampleSelective(userMap, example);
        if (userResult == 1) {
            return Result.success(1);
        }
        return Result.success(0);
    }

    /**
     * MySQL user 查询所有
     *
     * @return
     */
    @Override
    public List<User> queryAllUser() {
        UserExample example = new UserExample();
        example.setOrderByClause("`datetimes` DESC");
        return userMapper.selectByExample(example);
    }

    /**
     * MySQL 与原始密码比较
     *
     * @param oldPassword
     */
    @Override
    public Result oldPasswordToCompare(String oldPassword, String nickName) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNicknameEqualTo(nickName);
        List<User> userList = userMapper.selectByExample(example);
        boolean compareResult = userList.get(0).getPassword().equals(oldPassword);
        if (compareResult) {
            return Result.success(true);
        }
        return Result.success(false);
    }

    /**
     * MySQL 修改密码
     *
     * @param newPassword
     */
    @Override
    public Result ModifyPasswordToCompare(String newPassword, String nickName) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNicknameEqualTo(nickName);
        User user = new User();
        user.setPassword(newPassword);
        user.setDatetimes(new Date());
        int modifyPasswordResult = userMapper.updateByExampleSelective(user, example);
        List<User> userList = userMapper.selectByExample(example);
        User userInfo = userList.get(0);
        if (modifyPasswordResult > 0) {
            return Result.success(userInfo);
        }
        return Result.success(false);
    }

    /**
     * MySQL 根据id查询用户
     *
     * @return
     */
    @Override
    public Result queryUserById(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null && !user.toString().equals("")) {
            return Result.success(user);
        }
        return Result.success(false);
    }

    /**
     * MySQL 模糊查询用户
     *
     * @return
     */
    @Override
    public List<User> queryUserByFuzzy(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameLike("%" + username + "%");
        return userMapper.selectByExample(example);
    }

    /**
     * MySQL 批量删除
     *
     * @param ids
     */
    @Override
    public Result removeUserList(List<Integer> ids, int num) {
        List<Integer> idList = new ArrayList<>();
        for (int id : ids) {
            idList.add(id);
        }
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(idList);
        User user = new User();
        user.setLogicRemove(0);
        int userResult = userMapper.updateByExampleSelective(user, example);
        if (userResult == num) {
            return Result.success(1);
        }
        return Result.success(0);
    }

    /**
     * MySQL 条件查询用户
     *
     * @return
     */
    @Override
    public List<User> queryUserByConditions(ConditionsVo conditionsVo) {
        //判断输入性别为空时条件
        List<Integer> genderList = new ArrayList<>();
        genderList.add(0);
        genderList.add(1);
        //判断输入逻辑删除为空时条件
        List<Integer> logicRemoveList = new ArrayList<>();
        logicRemoveList.add(0);
        logicRemoveList.add(1);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (!String.valueOf(conditionsVo.getGender()).equals("")) {
            if (conditionsVo.getGender() == 2) {
                criteria.andGenderIn(genderList);
            } else {
                criteria.andGenderEqualTo(conditionsVo.getGender());
            }
        }
        if (conditionsVo.getRole() != null && !conditionsVo.getRole().isEmpty()) {
            criteria.andRoleEqualTo(conditionsVo.getRole());
        }
        if (!String.valueOf(conditionsVo.getLogicRemove()).equals("")) {
            if (conditionsVo.getLogicRemove() == 2) {
                criteria.andLogicRemoveIn(logicRemoveList);
            } else {
                criteria.andLogicRemoveEqualTo(conditionsVo.getLogicRemove());
            }
        }
        return userMapper.selectByExample(example);
    }

    /**
     * MySQL 查询用户数
     *
     * @return
     */
    @Override
    public Result queryUserCount() {
        UserExample example = new UserExample();
        int count = userMapper.countByExample(example);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failure(ResultCode.DATABASE_HAS_QUERY_ERROR,count);
    }

}
