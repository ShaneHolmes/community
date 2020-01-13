package com.shane.community.service;

import com.shane.community.dto.GithubUser;
import com.shane.community.mapper.UserMapper;
import com.shane.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private User user;
    @Autowired
    private UserMapper userMapper;

    public Boolean githubUserExist(GithubUser githubUser){
        List<String> accountIds = userMapper.getAccountIds();
        //System.out.println(accountIds);
        String id = String.valueOf(githubUser.getId());
        //System.out.println(id);
        for(String accountId : accountIds){
            //System.out.println(accountId);
            //System.out.println(id);

            if(accountId.equals(id)){
                //System.out.println("true");
                return true;
            }
        }
        return false;
    }

    public void insertGithubUser(GithubUser githubUser){
        user.setToken(UUID.randomUUID().toString());
        user.setName(githubUser.getName());
        user.setAccount_id(String.valueOf(githubUser.getId()));
        user.setGmt_create(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((new Date())));
        user.setGmt_modified(user.getGmt_create());
        user.setBio(githubUser.getBio());
        //System.out.println(user);
        userMapper.insert(user);
    }



    public void updateGithubUser(GithubUser githubUser) {
        userMapper.update(String.valueOf(githubUser.getId()),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((new Date())));
    }


    public User getUser(GithubUser githubUser){
        User user = userMapper.getUser(githubUser);
        //System.out.println(user);
        return user;
    }


}
