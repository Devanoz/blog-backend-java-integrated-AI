package com.devano.blog_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper mapper = Mappers.getMapper(CommentMapper.class);

}
