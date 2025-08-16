package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.request.UserRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.UserResponse;
import com.nguyenvanphuong.apple_devices.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    User toUser(UserRequest request);

    UserResponse toUserResponse(User user);
}
