package com.apartmentbuilding.PTIT.Mapper.User;

import com.apartmentbuilding.PTIT.DTO.Response.UserResponse;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserRegister;
import com.apartmentbuilding.PTIT.Model.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserMapper {
    UserResponse userEntityToUserResponse(UserEntity userEntity);
    UserEntity userRegisterToUserEntity(UserRegister userRegister);
}
