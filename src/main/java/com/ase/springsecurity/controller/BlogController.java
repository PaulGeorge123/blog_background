package com.ase.springsecurity.controller;

import com.ase.springsecurity.entity.mongodb.Blog;
import com.ase.springsecurity.entity.vo.SearchVo;
import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.service.BlogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author gyhstart
 * @create 2020/12/27 - 22:39
 **/

@RestController
@RequestMapping("mongodb")
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * MongoDB 增加
     *
     * @param blog
     */
    @PostMapping("/saveBlog")
    @ApiOperation(value = "增加博客", notes = "接口的详情描述")
    public Result saveMongoDB(@RequestBody Blog blog) {
        int blogMongoDB = blogService.saveBlogMongoDB(blog);
        return Result.success(blogMongoDB);
    }

    /**
     * MongoDB 删除
     *
     * @param id
     */
    @DeleteMapping("/removeBlog")
    @ApiOperation(value = "根据ID删除博客", notes = "接口的详情描述")
    public Result removeMongoDB(@RequestParam("id") long id) {
        return blogService.removeBlogMongoDB(id);
    }

    /**
     * MongoDB 修改
     *
     * @param blog
     */
    @PutMapping("/modifyBlog")
    @ApiOperation(value = "修改博客", notes = "接口的详情描述")
    public void updateMongoDB(@RequestBody Blog blog) {
        blogService.modifyBlogMongoDB(blog);
    }

    /**
     * MongoDB 查询所有
     *
     * @return
     */
    @GetMapping("/queryBlogAllList")
    @ApiOperation(value = "查询所有博客", notes = "接口的详情描述")
    public Result queryAllBlog(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "7") int pageSize) {
        return blogService.queryAllBlogMongoDB(pageNum, pageSize);
    }

    /**
     * MongoDB 查询ID
     *
     * @return
     */
    @GetMapping("/queryBlogById")
    @ApiOperation(value = "根据ID查询博客", notes = "接口的详情描述")
    public Result queryBlogById(@RequestParam("id") Long id) {
        return blogService.queryBlogById(id);
    }

    /**
     * MongoDB 模糊查询
     *
     * @return
     */
    @PostMapping("/queryFuzzyBlogList")
    @ApiOperation(value = "模糊查询博客", notes = "接口的详情描述")
    public Result queryFuzzyBlog(@RequestBody SearchVo searchVo,
                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "7") int pageSize) {
        return blogService.queryFuzzyBlogMongoDB(searchVo, pageNum, pageSize);
    }
}
