package com.devano.blog_app.mapper;

import com.devano.blog_app.entity.Post;
import com.devano.blog_app.request.post.CreatePostRequest;
import com.devano.blog_app.response.post.CreatePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostMapper {
    PostMapper mapper = Mappers.getMapper(PostMapper.class);

    Post mapToPostEntity(CreatePostRequest postRequest);

    List<CreatePostResponse> mapToCreatePostResponse(List<Post> posts);
}
