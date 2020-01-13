package com.shane.community.service;

import com.shane.community.mapper.UserMapper;
import com.shane.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class IndexService {

    @Autowired
    private UserMapper userMapper;

    public void index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();//从浏览器获取cookies
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){//浏览器中存在key为token的cookie
                    String token = cookie.getValue();//获取value
                    User user = userMapper.findByToken(token);//在数据库中找是否存在该value的user
                    if(user != null){
                        request.getSession().setAttribute("user",user);//把user添加到session
                    }
                    break;
                }
            }
        }

    }


}
