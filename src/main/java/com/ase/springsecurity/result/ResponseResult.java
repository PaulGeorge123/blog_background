package com.ase.springsecurity.result;

import com.ase.springsecurity.interfaces.IStatusMessage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gyhstart
 * @create 2021/1/3 - 14:20
 **/
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = -148117940294941578L;

    private String code;
    private String message;
    private Object obj;

    private Map<String,Object> data; //默认为hashMap,也可为对象

    public String getCode() {
        return code;
    }

    public ResponseResult() {
        this.code = IStatusMessage.SystemStatus.SUCCESS.getCode();
        this.message = IStatusMessage.SystemStatus.SUCCESS.getMessage();
    }

    public ResponseResult(IStatusMessage statusMessage){
        this.code = statusMessage.getCode();
        this.message = statusMessage.getMessage();

    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Map<String,Object> getData() {
        return data;
    }
    public void setData(Map<String,Object> data) {
        this.data = data;
    }

    public void putData(String key,Object value){
        if( this. data == null ){
            this.data = new HashMap<String,Object>();
        }
        this.data.put(key, value);
    }

}
