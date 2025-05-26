package com.apartmentbuilding.PTIT.Service.ServiceType;

import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.Model.Entity.ServiceTypeEntity;
import com.apartmentbuilding.PTIT.Repository.IServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServiceTypeImpl implements IServiceType {
    private final IServiceTypeRepository serviceTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public ServiceTypeEntity findByName(String name) {
        return this.serviceTypeRepository.findByName(name)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.SERVICE_TYPE_NOT_FOUND));
    }
}
