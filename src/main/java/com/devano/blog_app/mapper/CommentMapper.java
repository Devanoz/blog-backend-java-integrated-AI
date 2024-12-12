package com.devano.blog_app.mapper;

import com.devano.blog_app.entity.Comment;
import com.devano.blog_app.response.comment.CreateCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper mapper = Mappers.getMapper(CommentMapper.class);

    CreateCommentResponse commentToCreateCommentResponse(Comment comment);
}
