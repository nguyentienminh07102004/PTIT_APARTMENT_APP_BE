package com.apartmentbuilding.PTIT.Service.Role;

import com.apartmentbuilding.PTIT.Model.Entity.RoleEntity;

public interface IRoleService {
    RoleEntity findByCode(String code);
    RoleEntity findById(String id);
    RoleEntity save(RoleEntity role);
    boolean existsByCode(String code);
}
