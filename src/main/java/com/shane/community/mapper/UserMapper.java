package com.shane.community.mapper;

import com.shane.community.dto.GithubUser;
import com.shane.community.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,bio,avatar_url) values (#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified},#{bio},#{avatar_url})")
    void insert(User user);

    @Select("select * from user where account_id = #{id}")
    User getUser(GithubUser githubUser);

    @Select("select account_id from user")
    List<String> getAccountIds();

    @Update("update user set gmt_modified=#{gmt_modified} where account_id=#{account_id}")
    void update(@Param("account_id") String account_id, @Param("gmt_modified") String gmt_modified);


}