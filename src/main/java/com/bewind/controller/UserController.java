package com.bewind.controller;

import com.alibaba.fastjson.JSON;
import com.bewind.pojo.Role;
import com.bewind.pojo.User;
import com.bewind.service.RoleService;
import com.bewind.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@SessionAttributes("userSession")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * @param queryUserName required默认为true，表示请求路径上必须含有该值
     * @param queryUserRole
     * @param model
     * @return
     */
    @RequestMapping("/userQuery")
    public String userQuery(@RequestParam(value = "queryname",required = false) String queryUserName,
                            @RequestParam(value = "queryUserRole",required = false) Integer queryUserRole,
                            @RequestParam(value = "pageIndex",required = false) Integer pageIndex,
                            Model model){
        //分页插件没有生效，暂时放下
        pageIndex = pageIndex == null ? 1:pageIndex;
        //使用分页插件 startPage(当前页数,每页显示条数)
        PageHelper.startPage(pageIndex,8);
        List<User> userList = userService.getUserList(queryUserName, queryUserRole);
        //获取分页详细信息
        PageInfo<User> page = new PageInfo<>(userList);
        List<Role> roleList = roleService.getRoleList();
        model.addAttribute("userList",userList);
        model.addAttribute("roleList",roleList);
        model.addAttribute("queryUserName",queryUserName);
        model.addAttribute("queryUserRole",queryUserRole);
        model.addAttribute("totalPageCount",page.getPages());
        model.addAttribute("currentPageNo",pageIndex);
        model.addAttribute("isPrev", page.isHasPreviousPage());
        model.addAttribute("isNext", page.isHasNextPage());
        System.out.println(page.getPages());
        System.out.println(pageIndex);
        System.out.println(page.isHasPreviousPage());
        System.out.println(page.isHasNextPage());
        int[] counts = new int[page.getPages()];
        for (int i = 0; i < counts.length; i++) {
            counts[i] = i+1;
        }
        model.addAttribute("Counts",counts);
        return "userlist";
    }

    @ResponseBody
    @RequestMapping("/getrolelist")
    public String getRoleList(Model model) {
        List<Role> roleList = roleService.getRoleList();
        String json = JSON.toJSONString(roleList);
        return json;
    }

    /**
     * 查询用户入口
     * @return
     */
    @RequestMapping("/touseradd")
    public String touseradd(){
        return "useradd";
    }

//    @ResponseBody
//    @RequestMapping("/delUser")
//    public String delUser(@RequestParam("uid") Integer uid){
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("delResult","");
//
//        String json = JSON.toJSONString(hashMap);
//        return json;
//    }
}
