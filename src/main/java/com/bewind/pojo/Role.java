package com.bewind.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Role {
    private Integer id;
    private String roleCode;
    private String roleName;
    private Integer createBy;
    private Date createDate;
    private Integer modifyBy;
    private Date modifyDate;
}
