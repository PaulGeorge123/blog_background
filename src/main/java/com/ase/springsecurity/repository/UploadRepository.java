package com.ase.springsecurity.repository;

import com.ase.springsecurity.entity.easyexcel.UploadUserData;
import com.ase.springsecurity.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gyhstart
 * @create 2021/2/16 - 20:58
 *
 * 假设这个是你的DAO存储。当然还要这个类让spring管理，当然你不用需要存储，也不需要这个类。
 *
 **/
@Log4j2
@Repository
public class UploadRepository {

    @Autowired
    private UserMapper userMapper;

    /**
     * 持久化数据库 Excel
     */
    public void save(List<UploadUserData> list) {
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
        log.info("=======>",list);

    }
}