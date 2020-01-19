package com.shane.community.service;

import com.shane.community.mapper.UserMapper;
import com.shane.community.model.User;
import com.shane.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);//在数据库中找是否存在该value的user
                    if(! users.isEmpty()){
                        request.getSession().setAttribute("user",users.get(0));//把user添加到session
                    }
                    break;
                }
            }
        }

    }


}
