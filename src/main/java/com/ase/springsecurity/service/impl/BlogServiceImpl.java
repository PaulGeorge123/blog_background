package com.ase.springsecurity.service.impl;

import com.ase.springsecurity.entity.mongodb.Blog;
import com.ase.springsecurity.entity.vo.SearchVo;
import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.result.ResultCode;
import com.ase.springsecurity.service.BlogService;
import com.ase.springsecurity.utils.mongo.MongoUtil;
import com.ase.springsecurity.utils.mongo.PageHelper;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author gyhstart
 * @create 2020/12/27 - 22:35
 **/
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoUtil mongoUtil;

    /**
     * MongoDB 增加
     *
     * @param blog
     */
    @Override
    public int saveBlogMongoDB(Blog blog) {
        try {
            Blog blogSave = new Blog()
                    .setTitle(blog.getTitle())
                    .setContent(blog.getContent())
                    .setAuthorId(blog.getAuthorId())
                    .setAuthor(blog.getAuthor())
                    .setDatetime(new Date())
                    .setCategory(blog.getCategory())
                    .setThumb_up(blog.getThumb_up())
                    .setCollection(blog.getCollection())
                    .setAttention(blog.getAttention())
                    .setBrowse(blog.getBrowse());
            mongoTemplate.insert(blogSave, "blog");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * MongoDB 删除
     *
     * @param id
     */
    @Override
    public Result removeBlogMongoDB(long id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Blog.class);
        return Result.success();
    }

    /**
     * MongoDB 修改
     *
     * @param blog
     */
    @Override
    public long modifyBlogMongoDB(Blog blog) {
        Query query = new Query(Criteria
                .where("id").is(blog.getId()));
        Update update = new Update()
                .set("title", blog.getTitle())
                .set("content", blog.getContent())
//                .set("author", blog.getAuthor())
                .set("authorId",blog.getAuthorId())
                .set("datetime", new Date())
                .set("category", blog.getCategory())
                .set("thumb_up", blog.getThumb_up())
                .set("collection", blog.getCollection())
                .set("attention", blog.getAttention())
                .set("browse", blog.getBrowse());
        //更新查询返回的结果集的第一条数据
        UpdateResult result = mongoTemplate.upsert(query, update, Blog.class);
        //更新查询到的所有结果集
        //UpdateResult allResult = mongoTemplate.updateMulti(query,update,User.class);
        if (result != null) {
            return result.getModifiedCount();
        } else {
            return 0;
        }
    }

    /**
     * MongoDB 查询ID
     *
     * @return
     */
    @Override
    public Result queryBlogById(Long id) {
        Blog byId = mongoTemplate.findById(id, Blog.class);
        if (byId != null) {
            return Result.success(byId);
        } else {
            return Result.failure(ResultCode.DATABASE_HAS_QUERY_ERROR);
        }
    }

    /**
     * MongoDB 查询博客数
     *
     * @return
     */
    @Override
    public Result queryBlogCount() {
        long count = mongoTemplate.count(new Query(new Criteria()), Blog.class);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failure(ResultCode.DATABASE_HAS_QUERY_ERROR, count);
    }

    /**
     * MongoDB 精准查询（作者）
     *
     * @return
     */
    @Override
    public Result queryByAuthorBlogList(int authorId, int pageNum, int pageSize) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(criteria.and("authorId").is(authorId));
        return conditionsPagingResult(pageNum, pageSize, query);
    }

    /**
     * MongoDB 模糊查询title
     *
     * @return
     */
    @Override
    public Result queryByTitleBlogMongoDB(String title, int pageNum, int pageSize) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        Pattern pattern = Pattern.compile("^.*" + title + ".*$", Pattern.CASE_INSENSITIVE);
        query.addCriteria(criteria.and("title").regex(pattern));
        return conditionsPagingResult(pageNum, pageSize, query);
    }

    /**
     * MongoDB 查询所有
     *
     * @return
     */
    @Override
    public Result queryAllBlogMongoDB(int pageNum, int pageSize) {
        Query query = new Query(new Criteria());
        return conditionsPagingResult(pageNum, pageSize, query);
    }

    /**
     * MongoDB 模糊查询
     *
     * @param searchVo
     * @return
     */
    @Override
    public Result queryFuzzyBlogMongoDB(SearchVo searchVo, int pageNum, int pageSize) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (searchVo.getTitle() != null && searchVo.getTitle() != "") {
            Pattern pattern = Pattern.compile("^.*" + searchVo.getTitle() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(criteria.and("title").regex(pattern));
        }
        if (searchVo.getCategory() != null && searchVo.getCategory() != "") {
            Pattern pattern = Pattern.compile("^.*" + searchVo.getCategory() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(criteria.and("title").regex(pattern));
        }
        if (searchVo.getAuthor() != null && searchVo.getAuthor() != "") {
            Pattern pattern = Pattern.compile("^.*" + searchVo.getAuthor() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(criteria.and("title").regex(pattern));
        }
        return conditionsPagingResult(pageNum, pageSize, query);
    }

    /**
     * 分页条件查询
     *
     * @param pageNum
     * @param pageSize
     * @param query
     * @return
     */
    private Result conditionsPagingResult(int pageNum, int pageSize, Query query) {
        mongoUtil.start(pageNum, pageSize, query);
        List<Blog> blogList = mongoTemplate.find(query, Blog.class);
        long count = mongoTemplate.count(query, Blog.class);
        PageHelper pageHelper = mongoUtil.pageHelper(count, blogList);
        if (pageHelper != null) {
            return Result.success(pageHelper);
        } else {
            return Result.failure(ResultCode.DATABASE_HAS_QUERY_ERROR);
        }
    }
}
