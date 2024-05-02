package com.itheima.domain;

import lombok.Data;

import java.io.Serializable;

@Data   //相当于getter和setter方法
public class User implements Serializable {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String role;
    private String status;

}
