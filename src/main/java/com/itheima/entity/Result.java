package com.itheima.entity;
import lombok.Data;

import java.io.Serializable;
@Data
public class Result<T> implements Serializable {
    private boolean success;    //标识是否成功操作
    private String message;     //需要传递的信息
    private T data;             //需要传递的数据
    public Result(boolean success, String message) {
        super();
        this.success = success;
        this.message = message;
    }
    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
