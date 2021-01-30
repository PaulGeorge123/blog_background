package com.ase.springsecurity.result;

import net.sf.jsqlparser.schema.Database;

/**
 * @author gyhstart
 * @create 2020/11/29 - 22:39
 **/
public enum ResultCode {
    //成功
    SUCCESS(200,"成功"),
    //参数错误100-199
    PARAM_IS_INVALID(101,"参数无效"),
    PARAM_IS_BLANK(102,"参数为空"),
    PARAM_TYPE_BIND_ERROR(103,"参数类型错误"),
    PARAM_NOT_COMPLETE(104,"参数缺失"),
    //用户错误200-299
    USER_NOT_LOGGED_IN(201,"用户未登录，访问的路径需要验证，请登录"),
    USER_LOGIN_ERROR(202,"账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(203,"账号已被禁用"),
    USER_NOT_EXIST(204,"用户不存在"),
    USER_HAS_EXISTED(205,"用户已存在"),

    PATH_HAS_ERROR(404,"路径错误"),
    REQUEST_HAS_COLLAPSE(400,"请求错误"),
    SERVER_HAS_COLLAPSE(500,"服务器崩溃"),
    DATABASE_HAS_QUERY_ERROR(600,"数据库查询错误"); //快捷键 Ctrl+Shift+u即可实现大小写的快速切换

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}