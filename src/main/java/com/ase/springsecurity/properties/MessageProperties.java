package com.ase.springsecurity.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author gyhstart
 * @create 2021/1/3 - 14:07
 **/
@Component
@ConfigurationProperties(prefix="message")
@PropertySource("classpath:file-message.properties")
public class MessageProperties {
    /**
     * 压缩大小
     */
    private long fileSize;
    /**
     * 压缩比例
     */
    private double scaleRatio;
    /**
     * 保存路径
     */
    private String upPath;
    /**
     * 图片类型
     */
    private String imageType;

    /**
     * Markdown文件保存路径
     */
    private String markdownPath;

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public double getScaleRatio() {
        return scaleRatio;
    }

    public void setScaleRatio(double scaleRatio) {
        this.scaleRatio = scaleRatio;
    }

    public String getUpPath() {
        return upPath;
    }

    public void setUpPath(String upPath) {
        this.upPath = upPath;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getMarkdownPath() {
        return markdownPath;
    }

    public void setMarkdownPath(String markdownPath) {
        this.markdownPath = markdownPath;
    }

}