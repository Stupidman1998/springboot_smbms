package com.bewind.controller;

import com.alibaba.fastjson.JSON;
import com.bewind.pojo.User;
import com.bewind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashMap;

@SessionAttributes("userSession")
@Controller
public class PwdModifyController {

    @Autowired
    private UserService userService;


    /**
     * 主页跳转密码修改页面
     *
     * @param model
     * @return 页面名
     */
    @RequestMapping("/toPwdmodify")
    public String toPwdModify(Model model) {
        System.out.println("in pwd");
        return "pwdmodify";
    }

    /**
     * 处理从pwdmodify.js的ajax请求
     *
     * @param oldpassword
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/pwdModify")
    public String pwdModify(@RequestParam("oldpassword") String oldpassword, @ModelAttribute("userSession") User user) {
        System.out.println(user.getUserPassword());
        System.out.println(oldpassword);
        HashMap<String, Object> hashMap = new HashMap<>();
        if (user == null) {
            hashMap.put("result", "sessionerror");
        } else {
            if (oldpassword == "") {
                hashMap.put("result", "error");
            } else {
                if (oldpassword.equals(user.getUserPassword())) {
                    hashMap.put("result", "true");
                } else {
                    hashMap.put("result", "false");
                }
            }
        }
        String json = JSON.toJSONString(hashMap);
        return json;
    }

    /**
     * 更改密码
     *
     * @param model
     * @param newpassword 前端传过来的新密码
     * @param user        从Session中获得的值
     * @param status      更改成功后将Session消除
     * @return
     */
    @RequestMapping("/updatePwd")
    public String updatePwd(Model model, @RequestParam("newpassword") String newpassword,
                            @ModelAttribute("userSession") User user, SessionStatus status) {
        if (userService.updatePwd(user.getId(), newpassword) != 0) {
            model.addAttribute("message", "更改成功，请重新登录");
            status.setComplete();
        } else {
            model.addAttribute("message", "更改失败");
        }
        return "pwdmodify";
    }
}
