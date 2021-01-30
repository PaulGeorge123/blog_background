package com.ase.springsecurity.controller;

import com.alibaba.fastjson.JSONObject;
import com.ase.springsecurity.exception.ServiceException;
import com.ase.springsecurity.interfaces.IStatusMessage;
import com.ase.springsecurity.result.ResponseResult;
import com.ase.springsecurity.result.Result;
import com.ase.springsecurity.service.FileUpAndDownService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gyhstart
 * @create 2021/1/3 - 14:12
 **/
@Log4j2
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private FileUpAndDownService fileUpAndDownService;

    @RequestMapping(value = "/setFileUpload", method = RequestMethod.POST)
    public ResponseResult setFileUpload(MultipartFile[] file, String username) {
        ResponseResult result = new ResponseResult();
        try {
            Map<String, Object> resultMap = upload(file, username);
            if (!IStatusMessage.SystemStatus.SUCCESS.getMessage().equals(resultMap.get("result"))) {
                result.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
                result.setMessage((String) resultMap.get("msg"));
                return result;
            }
            result.setData(resultMap);
        } catch (ServiceException e) {
            e.printStackTrace();
            log.error(">>>>>>图片上传异常，e={}", e.getMessage());
            result.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
            result.setMessage(IStatusMessage.SystemStatus.FILE_UPLOAD_ERROR.getMessage());
        }
        return result;
    }

    private Map<String, Object> upload(MultipartFile[] file, String username) throws ServiceException {
        Map<String, Object> returnMap = new HashMap<>();
        try {
            if (file != null) {
                Map<String, Object> picMap = fileUpAndDownService.uploadPicture(file, username);
                if (IStatusMessage.SystemStatus.SUCCESS.getMessage().equals(picMap.get("result"))) {
                    return picMap;
                } else {
                    returnMap.put("result", IStatusMessage.SystemStatus.ERROR.getMessage());
                    returnMap.put("msg", picMap.get("result"));
                }
            } else {
                log.info(">>>>>>上传图片为空文件");
                returnMap.put("result", IStatusMessage.SystemStatus.ERROR.getMessage());
                returnMap.put("msg", IStatusMessage.SystemStatus.FILE_UPLOAD_NULL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(IStatusMessage.SystemStatus.FILE_UPLOAD_ERROR.getMessage());
        }
        return returnMap;
    }


    /**
     * 上传md文件
     *
     * @param param
     * @return
     */
    @PostMapping("/saveMd")
    //注意参数传递是以json格式，因此用@RequestBody定义接收参数
    public Result saveMd(@RequestBody JSONObject param) {
        return fileUpAndDownService.uploadMarkdownFile(param);
    }
}
