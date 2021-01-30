package com.ase.springsecurity.interfaces;

/**
 * @author gyhstart
 * @create 2021/1/3 - 14:19
 **/
public interface IStatusMessage {
    String getCode();
    String getMessage();

    enum SystemStatus implements IStatusMessage {
        /**
         * 请求成功
         */
        SUCCESS("1000", "操作成功"),
        /**
         * 请求失败
         */
        ERROR("1001", "网络异常，请稍后重试~"),
        /**
         * 上传图片为空文件
         */
        FILE_UPLOAD_NULL("1002","上传图片为空文件"),

        /**
         * 图片上传异常
         */
        FILE_UPLOAD_ERROR("1003","图片上传异常"),

        /**
         * 请求参数异常
         */
        PARAM_ERROR("1004","请求参数异常"),

        /**
         * 请求参数异常
         */
        INSERT_MONGODB_ERROR("1005","存入MongoDB错误"),;

        private String code;
        private String message;

        SystemStatus(String code, String message) {
            this.code = code;
            this.message = message;
        }
        @Override
        public String getCode() {
            return this.code;
        }
        @Override
        public String getMessage() {
            return this.message;
        }
    }
}
