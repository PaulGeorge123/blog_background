package com.ase.springsecurity.utils.mongo;

import lombok.Data;

import java.util.List;

/**
 * @author gyhstart
 * @create 2021/1/27 - 20:45
 **/
@Data
public class PageHelper<T> {
    private long currentPage;
    private long total;
    private long pageSize;
    private List<T> list;

    public PageHelper(long pageNum, long total, long pageSize, List<T> list) {
        this.currentPage = pageNum;
        this.total = total;
        this.pageSize = pageSize;
        this.list = list;
    }

    public PageHelper(long pageNum, long pageSize, List<T> list) {
        this.currentPage = pageNum;
        this.pageSize = pageSize;
        this.list = list;
    }
}