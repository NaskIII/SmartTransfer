package com.wayon.SmartTransfer.mapper;

import com.wayon.SmartTransfer.dto.user.UserBasicResponse;
import com.wayon.SmartTransfer.dto.user.UserCreateRequest;
import com.wayon.SmartTransfer.dto.user.UserDetailResponse;
import com.wayon.SmartTransfer.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    default Page<UserBasicResponse> UserToPageUserBasicResponse(Page<User> userPage) {
        List<UserBasicResponse> userBasicResponseList = userPage.getContent().stream()
                .map(this::UserToUserBasicResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(userBasicResponseList, userPage.getPageable(), userPage.getTotalElements());
    }

    @Mapping(target = "profile", ignore = true)
    User CreateUserRequestToUser(UserCreateRequest userCreateRequest);

    @Mapping(target = "profile", ignore = true)
    UserBasicResponse UserToUserBasicResponse(User user);

    @Mapping(target = "profile", ignore = true)
    UserDetailResponse UserToUserDetailResponse(User user);

}
