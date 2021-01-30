package com.ase.springsecurity.controller;

import com.ase.springsecurity.entity.User;
import com.ase.springsecurity.entity.vo.ConditionsVo;
import com.ase.springsecurity.entity.vo.IdsVo;
import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.result.ResultCode;
import com.ase.springsecurity.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gyhstart
 * @create 2020/12/28 - 22:10
 **/
@Log4j2
@Api(value = "用户相关接口", tags = "UserController")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * MySQL 增加
     *
     * @param user
     */
    @PostMapping("/saveUser")
    @ApiOperation(value = "增加用户", notes = "接口的详情描述")
    @ApiImplicitParam(name = "User", value = "请传递一个User实体", required = false, dataType = "String", paramType = "query")
    public Result saveUser(User user) {
        return userService.saveUser(user);
    }

    /**
     * MySQL 删除
     *
     * @param id
     */
    @DeleteMapping("/removeUser")
    @ApiOperation(value = "根据id删除用户(逻辑删除)", notes = "接口的详情描述")
    @ApiImplicitParam(name = "id", value = "请传递一个id", required = false, dataType = "int", paramType = "query")
    public Result removeUser(@RequestParam("id") int id) {
        return userService.removeUser(id);
    }

    /**
     * MySQL 批量删除
     *
     * @param idsVo
     */
    @PostMapping("/removeUserList")
    @ApiOperation(value = "根据id删除用户(逻辑删除)", notes = "接口的详情描述")
    @ApiImplicitParam(name = "idList", value = "请传递idList", required = false, dataType = "int", paramType = "query")
    public Result removeUserList(@RequestBody IdsVo idsVo) {
        return userService.removeUserList(idsVo.getIds(), idsVo.getNum());
    }

    /**
     * MySQL 修改
     *
     * @param user
     */
    @PutMapping("/modifyUser")
    @ApiOperation(value = "修改用户信息", notes = "接口的详情描述")
    @ApiImplicitParam(name = "User", value = "请传递一个User实体", required = false, dataType = "String", paramType = "query")
    public Result modifyUser(User user) {
        return userService.modifyUser(user);
    }

    /**
     * MySQL 查询所有
     *
     * @return
     */
    @GetMapping("/queryUserAllList")
    @ApiOperation(value = "查询所有用户", notes = "接口的详情描述")
    @ApiImplicitParam(name = "null", value = "无需传参", required = false)
    public Result queryAllUser() {
        List<User> userList = userService.queryAllUser();
        if (userList.size() > 0) {
            return Result.success(userList);
        }
        return Result.failure(ResultCode.SERVER_HAS_COLLAPSE);
    }

    /**
     * MySQL 根据id查询用户
     *
     * @return
     */
    @GetMapping("/queryUserById")
    @ApiOperation(value = "根据id查询用户", notes = "接口的详情描述")
    @ApiImplicitParam(name = "id", value = "请传递一个id", required = false, dataType = "int", paramType = "query")
    public Result queryUserById(@RequestParam("id") int id) {
        return userService.queryUserById(id);
    }


    /**
     * MySQL 模糊查询用户
     *
     * @return
     */
    @GetMapping("/queryUserByFuzzy")
    @ApiOperation(value = "模糊查询用户", notes = "接口的详情描述")
    @ApiImplicitParam(name = "name", value = "请传递一个name", required = false, dataType = "String", paramType = "query")
    public Result queryUserByFuzzy(@RequestParam("username") String username,
                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "7") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userService.queryUserByFuzzy(username);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return Result.success(pageInfo);
    }

    /**
     * MySQL 条件查询用户
     *
     * @return
     */
    @PostMapping("/queryUserByConditions")
    @ApiOperation(value = "模糊查询用户", notes = "接口的详情描述")
    @ApiImplicitParam(name = "conditionsVo", value = "请传递一个条件", required = false, dataType = "String", paramType = "query")
    public Result queryUserByConditions(ConditionsVo conditionsVo,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "7") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userService.queryUserByConditions(conditionsVo);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return Result.success(pageInfo);
    }


    /**
     * MySQL 查询用户 (分页)
     *
     * @return
     */
    @GetMapping("/queryUserPaging")
    @ApiOperation(value = "根据id查询用户", notes = "接口的详情描述")
    @ApiImplicitParam(name = "pageNum,pageSize", value = "请传递两个个参数pageNum,pageSize", required = false, dataType = "int", paramType = "query")
    public Result queryUserPaging(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userService.queryAllUser();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return Result.success(pageInfo);
    }

}
