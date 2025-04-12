package com.apartmentbuilding.PTIT.Service.Role;

import com.apartmentbuilding.PTIT.Model.Entity.RoleEntity;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.ExceptionVariable;
import com.apartmentbuilding.PTIT.Repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public RoleEntity findByCode(String code) {
        return roleRepository.findByCode(code)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.ROLE_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public RoleEntity findById(String id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.ROLE_NOT_FOUND));
    }

    @Override
    @Transactional
    public RoleEntity save(RoleEntity role) {
        return roleRepository.save(role);
    }

    @Override
    public boolean existsByCode(String code) {
        return roleRepository.existsByCode(code);
    }
}
