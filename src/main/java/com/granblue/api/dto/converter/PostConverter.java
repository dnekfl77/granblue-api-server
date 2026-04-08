package com.granblue.api.dto.converter;

import com.granblue.api.dto.response.PostResponse;
import com.granblue.api.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostConverter {
    @Mapping(source = "author.name", target = "authorName")
    PostResponse toResponse(Post post);
}