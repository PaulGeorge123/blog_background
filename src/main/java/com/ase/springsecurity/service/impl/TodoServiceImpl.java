package com.ase.springsecurity.service.impl;

import com.ase.springsecurity.entity.Todo;
import com.ase.springsecurity.entity.TodoExample;
import com.ase.springsecurity.mapper.TodoMapper;
import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.result.ResultCode;
import com.ase.springsecurity.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author gyhstart
 * @create 2021/1/28 - 22:06
 **/
@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoMapper todoMapper;

    /**
     * MySQL 增加待办事项
     *
     * @param todo
     */


    @Override
    public Result saveTodo(Todo todo) {
        todo.setDateday(new Date());
        todo.setLogicRemove(1);
        todo.setStatus(1);
        int insertTodo = todoMapper.insert(todo);
        if (insertTodo == 1) {
            return Result.success(ResultCode.SUCCESS);
        }
        return Result.failure(ResultCode.SERVER_HAS_COLLAPSE, 0);
    }

    /**
     * MySQL 删除待办事项
     *
     * @param id
     */
    @Override
    public Result removeTodo(int id) {
        TodoExample example = new TodoExample();
        TodoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        Todo todo = new Todo();
        todo.setLogicRemove(0);
        int remove = todoMapper.updateByExampleSelective(todo, example);
        if (remove == 1) {
            return Result.success(ResultCode.SUCCESS);
        }
        return Result.failure(ResultCode.SERVER_HAS_COLLAPSE, 0);

    }

    /**
     * MySQL 查询待办事项(分页)
     *
     * @return
     */
    @Override
    public List<Todo> queryAllTodo() {
        TodoExample example = new TodoExample();
        TodoExample.Criteria criteria = example.createCriteria();
        criteria.andLogicRemoveEqualTo(1);
        example.setOrderByClause("`status` ASC,`dateday` DESC");
        return todoMapper.selectByExample(example);
    }

    /**
     * MySQL 更新待办事项状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Result modifyTodo(int id, int status) {
        TodoExample example = new TodoExample();
        TodoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        Todo todo = new Todo();
        todo.setStatus(status);
        int modify = todoMapper.updateByExampleSelective(todo, example);
        if (modify == 1) {
            return Result.success(ResultCode.SUCCESS);
        }
        return Result.failure(ResultCode.SERVER_HAS_COLLAPSE, 0);
    }
}
