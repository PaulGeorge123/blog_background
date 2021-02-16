package com.ase.springsecurity.service;

import com.ase.springsecurity.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gyhstart
 * @create 2021/2/16 - 19:12
 **/
public interface EasyExcelService {
    /**
     * 文件下载
     */
    void template(HttpServletResponse response) throws IOException;

    /**
     * 文件上传
     */
    Result upload(MultipartFile file) throws IOException;

    /**
     * 文件下载
     */
    void download(HttpServletResponse response) throws IOException;
}
