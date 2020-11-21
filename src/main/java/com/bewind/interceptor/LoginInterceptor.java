package com.bewind.interceptor;

import com.bewind.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("LoginInterceptor--->:In preHandle");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userSession");
        if(user!=null){
            System.out.println("LoginInterceptor--->:In preHandle:true");
            return true;
        }

        System.out.println("LoginInterceptor--->:In preHandle:false");
        response.sendRedirect("/index");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
