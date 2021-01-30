package com.ase.springsecurity.service;

import com.ase.springsecurity.entity.mongodb.Blog;
import com.ase.springsecurity.entity.vo.SearchVo;
import com.ase.springsecurity.result.Result;

/**
 * @author gyhstart
 * @create 2020/12/27 - 22:32
 **/
public interface BlogService {
    /**
     * MongoDB 增加
     *
     * @param blog
     */
    int saveBlogMongoDB(Blog blog);

    /**
     * MongoDB 删除
     *
     * @param id
     */
    Result removeBlogMongoDB(long id);

    /**
     * MongoDB 修改
     *
     * @param blog
     */
    long modifyBlogMongoDB(Blog blog);

    /**
     * MongoDB 查询所有
     *
     * @return
     */
    Result queryAllBlogMongoDB(int pageNum,int pageSize);

    /**
     * MongoDB 模糊查询
     *
     * @param searchVo
     *
     * @return
     */
    Result queryFuzzyBlogMongoDB(SearchVo searchVo, int pageNum, int pageSize);

    /**
     * MongoDB 查询ID
     * @return
     */
    Result queryBlogById(Long id);
}
