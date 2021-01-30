package com.ase.springsecurity.repository;

import com.ase.springsecurity.entity.mongodb.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author gyhstart
 * @create 2020/12/27 - 11:58
 *
 * 用户的dao层的接口，实现MongoRepository
 * 第一个泛型指定的是实体类，第二个指定的是主键Id的类型
 **/

public interface BlogMongoDBRepository extends MongoRepository<Blog, Long> {
}
