package com.shane.community.service;

import com.shane.community.dto.AccessTokenDTO;
import com.shane.community.dto.GithubUser;
import com.shane.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthorizeService {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    public GithubUser getGithubUser(String code,String state){
        AccessTokenDTO accessTokenDTO = tokenService.initAccessTokenDTO(code,state);

        /**调用githubProvider.getAccessToken()方法获取accessToken*/
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);

        /**获取GithubUser对象*/
        GithubUser githubUser = githubProvider.getUser(accessToken);
        //System.out.println(githubUser);

        return githubUser;
    }

    public boolean githubAuthorize(GithubUser githubUser,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        if (githubUser != null) {/**授权登录成功，设置session和cookie,并存储到数据库中*/
            Boolean githubUserExist = userService.githubUserExist(githubUser);
            //System.out.println(githubUserExist);
            if (!githubUserExist) {
                userService.insertGithubUser(githubUser);
                //System.out.println("insert");
            } else {
                userService.updateGithubUser(githubUser);
                //System.out.println("update");
            }
            String token = userService.getUser(githubUser).getToken();
            request.getSession().setAttribute("user", githubUser);
            response.addCookie(new Cookie("token",token));
            return true;
        }
        return false;
    }
}
