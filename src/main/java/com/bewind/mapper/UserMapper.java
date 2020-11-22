package com.bewind.mapper;

import com.bewind.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Select("select * from smbms_user where userCode=#{userCode}")
    User getLoginUser(String userCode);

    @Update("UPDATE smbms_user set userPassword=#{password} WHERE id = #{id}")
    int updatePwd(int id, String password);

    //自定义查询注解
    @SelectProvider(type = generateSql.class, method = "getUserListByParam")
    List<User> getUserList(@Param("userName") String userName, @Param("userRole") Integer userRole);

    class generateSql {
        public String getUserListByParam(String userName, Integer userRole) {
            StringBuilder sql = new StringBuilder("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id ");
            if (userName != null && userName != " ") {
                sql.append("and u.userName like '%${userName}' ");
            }
            if (userRole != null && userRole > 0) {
                sql.append("and u.userRole = #{userRole} ");
            }
            return sql.toString();
        }
    }

//    @Insert("INSERT into smbms_user(userCode,userName,userPassword,gender,birthday,phone,address,userRole," +
//            "createBy,creationDate) " +
//            "VALUES(#{userCode},#{userName},#{userPassword},#{gender},#{birthday},#{phone},#{address},#{userRole}," +
//            "#{createBy},#{creationDate}")
//    int userAdd(@Param("userCode")String userCode, @Param("userName")String userName,
//                @Param("userPassword")String userPassword, @Param("gender")Integer gender,
//                @Param("birthday")Date birthday,@Param("phone")String phone,@Param("address")String address,
//                @Param("userRole")Integer userRole,@Param("createBy")Integer createBy,
//                @Param("creationDate")Date creationDate);

    @Insert("INSERT into smbms_user(userCode,userName,userPassword,gender,birthday,phone,address,userRole," +
            "createBy,creationDate) " +
            "VALUES(#{user.userCode},#{user.userName},#{user.userPassword},#{user.gender},#{user.birthday}," +
            "#{user.phone},#{user.address},#{user.userRole}," + "#{user.createBy},#{user.creationDate})")
    int userAdd(@Param("user") User user);
}
