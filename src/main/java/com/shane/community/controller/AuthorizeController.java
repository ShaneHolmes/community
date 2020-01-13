package com.shane.community.controller;

import com.shane.community.dto.GithubUser;
import com.shane.community.model.User;
import com.shane.community.service.AuthorizeService;
import com.shane.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthorizeService authorizeService;




    /**
     * Github三方登录的回调函数
     * Github授权的过程：
     * 1.点击登录按钮(超链接地址是https://github.com/login/oauth/authorize?client_id=Iv1.1e6d67d6f6025830&redirect_uri=http://localhost:8080/callback&scope=user&state=1)向GitHub发送授权请求
     * 2.GitHub验证成功后向重定向地址(redirect_uri=http://localhost:8080/callback)发送请求”/callback“，同时传回的参数有code和state
     * 3.服务器处理请求”/callback“，即进入此函数
     * 4.获得githubUser的信息：
     *      4.1 向服务器获取accessToken
     *      4.2 通过accessToken获取用户信息
     * 5.githubAuthorize验证是否成功获得授权信息
     * 6.授权成功，获取数据库中的相应GitHubUser信息，并重定向到主页；
     *   授权失败，重定向到主页
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){

        GithubUser githubUser = authorizeService.getGithubUser(code,state);

        boolean authorized = authorizeService.githubAuthorize(githubUser,request);

        if(authorized){
           User user = userService.getUser(githubUser);
           //System.out.println(user);
           return "redirect:/";
        }else{/**登录失败*/
         return "redirect:/";
        }
    }




}
