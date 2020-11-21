package com.bewind.service;

import com.bewind.pojo.User;

import java.util.List;

public interface UserService {
    User login(String userCode,String password);

    int updatePwd(int userId,String password);

    List<User> getUserList(String userName,Integer userRole);
}
