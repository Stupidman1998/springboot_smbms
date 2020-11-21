package com.bewind.mapper;

import com.bewind.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RoleMapper {

    @Select("SELECT * FROM smbms_role")
    List<Role> getRoleList();
}
