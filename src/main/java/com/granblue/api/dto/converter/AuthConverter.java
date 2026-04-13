package com.granblue.api.dto.converter;

import com.granblue.api.dto.request.SignUpRequest;
import com.granblue.api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthConverter {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(SignUpRequest request);
}