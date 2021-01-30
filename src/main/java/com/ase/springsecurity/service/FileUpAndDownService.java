package com.ase.springsecurity.service;

import com.alibaba.fastjson.JSONObject;
import com.ase.springsecurity.exception.ServiceException;
import com.ase.springsecurity.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author gyhstart
 * @create 2021/1/3 - 14:09
 **/
public interface FileUpAndDownService {
    /**
     * 图片上传 FileUpAndDown
     * @param file
     * @return
     * @throws ServiceException
     * @throws ServiceException
     */
    Map<String, Object> uploadPicture(MultipartFile[] file, String username) throws ServiceException;

    /**
     *  Markdown文件上传
     * @param param
     * @return
     */
    Result uploadMarkdownFile(JSONObject param);
}
