package com.ase.springsecurity.service;

import com.ase.springsecurity.entity.Todo;
import com.ase.springsecurity.result.Result;

import java.util.List;

/**
 * @author gyhstart
 * @create 2021/1/28 - 22:05
 **/
public interface TodoService {

    /**
     * MySQL 增加待办事项
     *
     * @param todo
     */
    Result saveTodo(Todo todo);

    /**
     * MySQL 删除待办事项
     *
     * @param id
     */
    Result removeTodo(int id);

    /**
     * MySQL 查询待办事项(分页)
     *
     * @return
     */
    List<Todo> queryAllTodo();

    /**
     * MySQL 更新待办事项状态
     *
     * @param id
     * @param status
     * @return
     */
    Result modifyTodo(int id, int status);
}
