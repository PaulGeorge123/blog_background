package com.ase.springsecurity.entity.mongodb.util;

import java.lang.annotation.*;

/**
 * @author gyhstart
 * @create 2020/12/27 - 16:43
 * 通过这个注解标识主键ID需要自动增长
 **/

/**
 * 定义一个主键自增的标志注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AutoInc {
}