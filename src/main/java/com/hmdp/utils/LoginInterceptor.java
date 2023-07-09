package com.hmdp.utils;

import com.hmdp.dto.UserDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 进入controller之前进行登录校验
         */
        //1.获取session
        HttpSession session = request.getSession();
        //2.获取session中的用户
        Object user = session.getAttribute("user");
        //3.校验用户是否存在
        if (user == null) {
            //4.不存在，拦截；
            //比较温柔的拦截，返回401未授权的状态码
            response.setStatus(401);
            return false;
        }

        //5.存在，保存用户信息到threadLocal
        //调用threadLocal的工具类UserHolder
        UserHolder.saveUser((UserDTO) user);
        //6.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /**
         * 用户业务执行完毕销毁用户信息,避免内存泄漏
         */
//        UserHolder.removeUser();
    }
}