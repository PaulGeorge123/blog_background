package com.ase.springsecurity.controller;

import com.ase.springsecurity.entity.User;
import com.ase.springsecurity.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gyhstart
 * @create 2020/11/29 - 22:43
 **/
@Api(value = "测试SwaggerAPI Annotation", tags = "Swagger测试之用户信息管理API")
@RestController
@RequestMapping("/user")
public class SwaggerController {

    @ApiIgnore    // 忽略这个API
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping(value = "/swaggerGet/{name}")
    @ApiOperation(value = "接口方法说明", notes = "接口的详情描述")
    @ApiImplicitParam(name = "name", value = "请传递一个用户名参数",required = false,dataType = "String", paramType = "path")
    public Result swaggerGet(@PathVariable String name) {
        User user =new User();
        user.setUsername(name);
        user.setPassword("123456");
        return Result.success(user);
    }
}
