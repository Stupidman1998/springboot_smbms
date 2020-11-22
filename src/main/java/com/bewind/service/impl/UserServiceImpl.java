package com.bewind.service.impl;

import com.bewind.mapper.UserMapper;
import com.bewind.pojo.User;
import com.bewind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @param userCode
     * @param password
     * @return
     */
    @Override
    public User login(String userCode, String password) {
        User user = userMapper.getLoginUser(userCode);
        if (null!=user){
            if (!user.getUserPassword().equals(password)) {
                user = null;
            }
        }
        return user;
    }

//    @Override
////    public int userAdd(String userCode, String userName, String userPassword, Integer gender, Date birthday,
////                       String phone, String address, Integer userRole, Integer createBy, Date creationDate) {
////        return userMapper.userAdd(userCode,userName,userPassword,gender,birthday,phone,address,userRole,createBy,creationDate);
////    }

    @Override
    @Transactional
    public int userAdd(User user) {
        return userMapper.userAdd(user);
    }

    @Override
    public boolean userCodeExist(String userCode) {
        User user = userMapper.getLoginUser(userCode);
        return user != null;
    }

    @Override
    public int updatePwd(int userId, String password) {
        return userMapper.updatePwd(userId, password);
    }

    @Override
    public List<User> getUserList(String userName, Integer userRole) {
        return userMapper.getUserList(userName,userRole);
    }
}
