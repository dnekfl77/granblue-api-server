package com.granblue.api.dto.converter;

import com.granblue.api.dto.request.SignUpRequest;
import com.granblue.api.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-21T00:23:47+0900",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class AuthConverterImpl implements AuthConverter {

    @Override
    public User toEntity(SignUpRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.accountId( request.getAccountId() );
        user.age( request.getAge() );
        user.birth( request.getBirth() );
        user.email( request.getEmail() );
        user.gender( request.getGender() );
        user.name( request.getName() );
        user.termsAgreed( request.getTermsAgreed() );

        return user.build();
    }
}
