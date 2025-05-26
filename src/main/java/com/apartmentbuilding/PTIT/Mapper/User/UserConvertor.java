package com.apartmentbuilding.PTIT.Mapper.User;

import com.apartmentbuilding.PTIT.DTO.Response.UserResponse;
import com.apartmentbuilding.PTIT.Mapper.Role.IRoleMapper;
import com.apartmentbuilding.PTIT.Model.Entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConvertor {
    private final IUserMapper userMapper;
    private final IRoleMapper roleMapper;

    public UserResponse toResponse(UserEntity user) {
        UserResponse userResponse = this.userMapper.userEntityToUserResponse(user);
        userResponse.setRole(this.roleMapper.toResponse(user.getRole()));
        return userResponse;
    }
}
