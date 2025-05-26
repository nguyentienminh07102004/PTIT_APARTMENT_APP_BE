package com.apartmentbuilding.PTIT.Mapper.Role;

import com.apartmentbuilding.PTIT.DTO.Response.RoleResponse;
import com.apartmentbuilding.PTIT.Model.Entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRoleMapper {
    RoleResponse toResponse(RoleEntity role);
}
