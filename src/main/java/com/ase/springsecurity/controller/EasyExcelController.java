package com.ase.springsecurity.controller;

import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.service.EasyExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gyhstart
 * @create 2021/2/16 - 19:13
 **/
@RestController
@RequestMapping("easyExcel")
public class EasyExcelController {

    @Autowired
    private EasyExcelService easyExcelService;

    /**
     * 文件下载
     * template 模板
     */
    @GetMapping("/template")
    public void downloadFailedUsingJson(HttpServletResponse response) throws IOException {
        easyExcelService.template(response);
    }

    /**
     * 文件下载
     * @since 2.1.1
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        easyExcelService.download(response);
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        return easyExcelService.upload(file);
    }
}
