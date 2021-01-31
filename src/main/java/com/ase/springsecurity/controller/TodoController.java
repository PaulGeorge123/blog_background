package com.ase.springsecurity.controller;

import com.ase.springsecurity.entity.Todo;
import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.service.TodoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gyhstart
 * @create 2021/1/28 - 22:04
 **/

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    /**
     * MySQL 增加待办事项
     *
     * @param todo
     */
    @PostMapping("/saveTodo")
    @ApiOperation(value = "增加待办事项", notes = "接口的详情描述")
    public Result saveTodo(@RequestBody Todo todo) {
        return todoService.saveTodo(todo);
    }

    /**
     * MySQL 删除待办事项
     *
     * @param id
     */
    @DeleteMapping("/removeTodo")
    @ApiOperation(value = "根据id删除用户(逻辑删除)", notes = "接口的详情描述")
    public Result removeTodo(@RequestParam("id") int id) {
        return todoService.removeTodo(id);
    }

    /**
     * MySQL 查询待办事项(分页)
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/queryTodoPaging")
    @ApiOperation(value = "查询待办事项(分页)", notes = "接口的详情描述")
    public Result queryTodoPaging(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "7") int pageSize) {
//        String orderBy="status ASC,dateday DESC";
        PageHelper.startPage(pageNum, pageSize);
        List<Todo> todoList = todoService.queryAllTodo();
        PageInfo<Todo> pageInfo = new PageInfo<>(todoList);
        return Result.success(pageInfo);
    }

    /**
     * MySQL 更新待办事项状态
     *
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/modifyTodo")
    @ApiOperation(value = "查询待办事项(分页)", notes = "接口的详情描述")
    public Result modifyTodo(@RequestParam("id") int id, @RequestParam("status") int status) {
        return todoService.modifyTodo(id, status);
    }
}
