package com.apartmentbuilding.PTIT.Mapper.User;

import com.apartmentbuilding.PTIT.DTO.Reponse.UserResponse;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserRegister;
import com.apartmentbuilding.PTIT.Domains.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserResponse userEntityToUserResponse(UserEntity userEntity);
    UserEntity userRegisterToUserEntity(UserRegister userRegister);
}
