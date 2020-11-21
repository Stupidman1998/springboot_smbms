package com.bewind.service.impl;

import com.bewind.mapper.UserMapper;
import com.bewind.pojo.User;
import com.bewind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public int updatePwd(int userId, String password) {
        return userMapper.updatePwd(userId, password);
    }

    @Override
    public List<User> getUserList(String userName, Integer userRole) {
        return userMapper.getUserList(userName,userRole);
    }
}
