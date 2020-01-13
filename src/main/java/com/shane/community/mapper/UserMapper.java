package com.shane.community.mapper;

import com.shane.community.dto.GithubUser;
import com.shane.community.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,bio,avatar_url) values (#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified},#{bio},#{avatar_url})")
    void insert(User user);//参数是类时会自动映射

    @Select("select * from user where account_id = #{id}")
    User getUser(GithubUser githubUser);

    @Select("select account_id from user")
    List<String> getAccountIds();

    @Update("update user set gmt_modified=#{gmt_modified} where account_id=#{account_id}")
    void update(@Param("account_id") String account_id, @Param("gmt_modified") String gmt_modified);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//参数不是类时要用@Param注解
}