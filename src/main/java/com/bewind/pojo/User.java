package com.bewind.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Integer id; //id
    private String userCode;    //用户编码
    private String userName;    //用户名
    private String userPassword;//密码
    private Integer gender;      //性别
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;    //生日
    private String phone;       //电话
    private String address;     //地址
    private Integer userRole;    //用户角色
    private Integer createBy;   //创建者
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;  //创建时间
    private Integer modifyBy;   //更新者
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyDate;    //更新时间

    private Integer age;
    private String userRoleName;    //用户角色名称

    public Integer getAge() {
        Date date = new Date();
        Integer age = date.getYear()-birthday.getYear();
        return age;
    }
}
