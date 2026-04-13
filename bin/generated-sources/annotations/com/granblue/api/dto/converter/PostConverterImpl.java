package com.granblue.api.dto.converter;

import com.granblue.api.dto.response.PostResponse;
import com.granblue.api.entity.Post;
import com.granblue.api.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-21T00:23:47+0900",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class PostConverterImpl implements PostConverter {

    @Override
    public PostResponse toResponse(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse.PostResponseBuilder postResponse = PostResponse.builder();

        postResponse.authorName( postAuthorName( post ) );
        postResponse.content( post.getContent() );
        postResponse.createdAt( post.getCreatedAt() );
        postResponse.id( post.getId() );
        postResponse.title( post.getTitle() );

        return postResponse.build();
    }

    private String postAuthorName(Post post) {
        if ( post == null ) {
            return null;
        }
        User author = post.getAuthor();
        if ( author == null ) {
            return null;
        }
        String name = author.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
