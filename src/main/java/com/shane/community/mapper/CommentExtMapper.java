package com.shane.community.mapper;


import com.shane.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}