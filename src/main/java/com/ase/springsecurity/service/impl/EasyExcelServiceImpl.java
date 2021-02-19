package com.ase.springsecurity.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.ase.springsecurity.entity.User;
import com.ase.springsecurity.entity.UserExample;
import com.ase.springsecurity.mapper.UserMapper;
import com.ase.springsecurity.repository.UploadRepository;
import com.ase.springsecurity.entity.easyexcel.DownloadUserData;
import com.ase.springsecurity.entity.easyexcel.UploadUserData;
import com.ase.springsecurity.listener.UploadDataListener;
import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.result.ResultCode;
import com.ase.springsecurity.service.EasyExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author gyhstart
 * @create 2021/2/16 - 19:12
 **/

@Service
public class EasyExcelServiceImpl implements EasyExcelService {

    @Autowired
    private UploadRepository uploadRepository;

    @Autowired
    private UserMapper userMapper;

    /**
     * 文件下载
     */
    @Override
    public void template(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("用户信息模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition",  "attachment;filename*=utf-8''" + fileName + ".xlsx");
        //设置排除属性
        Set<String> set = new HashSet<>();
        set.add("id");
        set.add("password");
        set.add("logicRemove");
        EasyExcel.write(response.getOutputStream(), DownloadUserData.class).excludeColumnFiledNames(set).sheet("模板").doWrite(templateData());
    }

    /**
     * 文件下载
     */
    @Override
    public void download(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("用户信息", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            //设置排除属性
            Set<String> set = new HashSet<>();
            set.add("id");
            set.add("password");
            set.add("logicRemove");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), DownloadUserData.class).excludeColumnFiledNames(set).autoCloseStream(Boolean.FALSE).sheet("用户列表")
                    .doWrite(data());
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    /**
     * 文件上传
     */
    @Override
    public Result upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), UploadUserData.class, new UploadDataListener(uploadRepository)).sheet().doRead();
        return Result.success(ResultCode.SUCCESS);
    }

    /**
     * 数据模板
     */
    private List templateData() {
        List<DownloadUserData> list = new ArrayList<>();
        DownloadUserData downloadUserData1 = new DownloadUserData("16044903","姓名","user",new Date(),1);
        list.add(downloadUserData1);
        return list;
    }

    /**
     * 数据
     */
    private List<User> data() {
        UserExample example = new UserExample();
        example.setOrderByClause("`datetimes` DESC");
        return userMapper.selectByExample(example);
    }
}
