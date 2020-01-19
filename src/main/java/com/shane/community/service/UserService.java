package com.shane.community.service;

import com.shane.community.dto.GithubUser;
import com.shane.community.mapper.UserMapper;
import com.shane.community.model.User;
import com.shane.community.model.UserExample;
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

        String gitHubUserId = String.valueOf(githubUser.getId());
        UserExample userExample=new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(gitHubUserId);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.isEmpty()){
            return false;
        }
        return true;

    }

    public void insertGithubUser(GithubUser githubUser){
        user.setToken(UUID.randomUUID().toString());
        user.setName(githubUser.getName());
        user.setAccountId(String.valueOf(githubUser.getId()));
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setBio(githubUser.getBio());
        user.setAvatarUrl(githubUser.getAvatarUrl());
        //System.out.println(user);
        userMapper.insert(user);
    }


    public void updateGithubUser(GithubUser githubUser) {
        user.setGmtModified(System.currentTimeMillis());
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(String.valueOf(githubUser.getId()));
        userMapper.updateByExample(user,userExample);
    }


    public List<User> getUser(GithubUser githubUser){
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(String.valueOf(githubUser.getId()));
        List<User> users = userMapper.selectByExample(userExample);
        //System.out.println(user);
        return users;
    }


}
