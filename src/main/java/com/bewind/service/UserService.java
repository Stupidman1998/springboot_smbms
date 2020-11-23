package com.bewind.service;

import com.bewind.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserService {
    User login(String userCode, String password);

    int updatePwd(int userId, String password);

    List<User> getUserList(String userName, Integer userRole);

    boolean userCodeExist(String userCode);

//    int userAdd(String userCode, String userName, String userPassword, Integer gender, Date birthday, String phone,
//                String address, Integer userRole, Integer createBy, Date creationDate);

    int userAdd(User user);

    User getUserById(Integer uid);

    int deleteById(Integer id);

    int updateUser(User user);

}
