package com.bewind.controller;

import com.bewind.pojo.User;
import com.bewind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("userSession")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 跳转主页
     *
     * @return
     */
    @RequestMapping({"/", "/index"})
    public String toIndex() {
        System.out.println("LoginController--->:toIndex");
        return "index";
    }

    /**
     * 验证账号信息
     *
     * @param userCode     获得前端传递的账户名称
     * @param userPassword 获得前端传递的密码
     * @param model
     * @return 重定向到个人页面
     */
    @RequestMapping(value = "/toLogin", method = RequestMethod.POST)
    public String toLogin(@RequestParam("userCode") String userCode, @RequestParam("userPassword") String userPassword, Model model) {
        User user = userService.login(userCode, userPassword);

        System.out.println("LoginController--->:In toLogin");

        if (null != user) {
            model.addAttribute("userSession", user);
            System.out.println("LoginController--->:toframe");
            return "redirect:frame";
        }

        System.out.println("LoginController--->:ERROR: Return Index");
        model.addAttribute("error", "账号密码不正确");
        return "redirect:index";
    }

    /**
     * 跳转个人页面
     *
     * @return
     */
    @RequestMapping("/frame")
    public String frame() {
        System.out.println("LoginController--->:toFrame");
        return "frame";
    }

    /**
     * 退出登录
     *
     * @param status 通过SessionStatus消除Session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(SessionStatus status) {
        //消除session
        status.setComplete();
        return "redirect:index";
    }
}
