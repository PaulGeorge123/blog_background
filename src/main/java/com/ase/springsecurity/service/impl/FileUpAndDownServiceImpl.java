package com.ase.springsecurity.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ase.springsecurity.entity.mongodb.Blog;
import com.ase.springsecurity.entity.mongodb.Context;
import com.ase.springsecurity.entity.mongodb.Photo;
import com.ase.springsecurity.exception.ServiceException;
import com.ase.springsecurity.interfaces.IStatusMessage;
import com.ase.springsecurity.properties.MessageProperties;
import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.result.ResultCode;
import com.ase.springsecurity.service.FileUpAndDownService;
import com.ase.springsecurity.utils.FileUtil;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author gyhstart
 * @create 2021/1/3 - 14:10
 **/
@Log4j2
@Service
public class FileUpAndDownServiceImpl implements FileUpAndDownService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 用来获取file-message.properties配置文件中的信息
     */
    @Autowired
    private MessageProperties config;


    /**
     * 图片上传 FileUpAndDown
     *
     * @param file
     * @return
     * @throws ServiceException
     * @throws ServiceException
     */
    @Override
    public Map<String, Object> uploadPicture(MultipartFile[] file, String username) throws ServiceException {
        try {
            Map<String, Object> resMap = new HashMap<>();
            String[] IMAGE_TYPE = config.getImageType().split(",");
            String path = null;
            boolean flag = false;
            for (MultipartFile multipartFile:file) {
                System.out.println("文件"+multipartFile.getOriginalFilename());
                for (String type : IMAGE_TYPE) {
                    if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(), type)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    resMap.put("result", IStatusMessage.SystemStatus.SUCCESS.getMessage());
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    // 获得文件类型
                    String fileType = multipartFile.getContentType();
                    // 获得文件后缀名称
                    String imageName = fileType.substring(fileType.indexOf("/") + 1);
                    // 原名称
                    String oldFileName = multipartFile.getOriginalFilename();
                    // 新名称
                    String newFileName = uuid + "." + imageName;
                    // 年月日文件夹
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String basedir = sdf.format(new Date());
                    // 进行压缩(大于4M)
                    if (multipartFile.getSize() > config.getFileSize()) {
                        // 重新生成
                        String newUUID = UUID.randomUUID().toString().replaceAll("-", "");
                        newFileName = newUUID + "." + imageName;
                        path = config.getUpPath() + "/" + basedir + "/" + newUUID + "." + imageName;
                        // 如果目录不存在则创建目录
                        File oldFile = new File(path);
                        if (!oldFile.exists()) {
                            oldFile.mkdirs();
                        }
                        multipartFile.transferTo(oldFile);
                        // 压缩图片
                        Thumbnails.of(oldFile).scale(config.getScaleRatio()).toFile(path);
                        // 显示路径
                        resMap.put("path", "/" + basedir + "/" + newUUID + "." + imageName);
                    } else {
                        path = config.getUpPath() + "/" + basedir + "/" + uuid + "." + imageName;
                        // 如果目录不存在则创建目录
                        File uploadFile = new File(path);
                        if (!uploadFile.exists()) {
                            uploadFile.mkdirs();
                        }
                        multipartFile.transferTo(uploadFile);
                        // 显示路径
                        resMap.put("path", "/" + basedir + "/" + uuid + "." + imageName);
                    }
                    resMap.put("oldFileName", oldFileName);
                    resMap.put("newFileName", newFileName);
                    resMap.put("fileSize", multipartFile.getSize());
                } else {
                    resMap.put("result", "图片格式不正确,支持png|jpg|jpeg");
                }
            }

            log.error(resMap);
            int savePhotoResult = savePhotoMongoDB(resMap, username);
            if (savePhotoResult == 1) {
                return resMap;
            } else {
                Map<String, Object> resMapFalse = new HashMap<>();
                resMapFalse.put("result", IStatusMessage.SystemStatus.INSERT_MONGODB_ERROR.getMessage());
                return resMapFalse;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * MongoDB 增加
     *
     * @param resMap
     * @param username
     */
    public int savePhotoMongoDB(Map resMap, String username) {
        try {
            Photo photo = new Photo()
                    .setUsername(username)
                    .setFileSize(resMap.get("fileSize").toString())
                    .setNewFileName(resMap.get("newFileName").toString())
                    .setOldFileName(resMap.get("oldFileName").toString())
                    .setPath(resMap.get("path").toString());
            mongoTemplate.insert(photo, "photo");
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Markdown文件上传
     *
     * @param param
     * @return
     */
    @Override
    public Result uploadMarkdownFile(JSONObject param) {
        Map<String, Object> resMap = new HashMap<>();
        //取出java中对应参数的值
        String content = param.getString("content");
        String title = param.getString("title");
        String username = param.getString("username");
        String category = param.getString("category");
        //文件保存路径  D:\ideaWorkspace\UploadData\markdown\xxx.md
        String filepath = config.getMarkdownPath() + "/" + title + ".md";
//        resMap.put("content",content);
//        resMap.put("title",title);
//        resMap.put("username",username);
        Blog blogSave = new Blog()
                .setTitle(title)
                .setContent(content)
                .setAuthor(username)
                .setDatetime(new Date())
                .setCategory(category)
                .setThumb_up(0)
                .setCollection(0)
                .setAttention(0)
                .setBrowse(0);
        log.error(resMap);
        int saveMarkdownFileResult = saveMarkdownFileMongoDB(blogSave);
        if (saveMarkdownFileResult == 1){
            return Result.success(FileUtil.string2File(content, filepath));
        }else {
            return Result.failure(ResultCode.SERVER_HAS_COLLAPSE);
        }
    }



    /**
     * MongoDB 增加
     *
     * @param blogSave
     */
    public int saveMarkdownFileMongoDB(Blog blogSave) {
        try {
//            Context contextSave = new Context()
//                    .setContent(resMap.get("content").toString())
//                    .setTitle(resMap.get("title").toString())
//                    .setUsername(resMap.get("username").toString());
            mongoTemplate.insert(blogSave, "blog");

            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}