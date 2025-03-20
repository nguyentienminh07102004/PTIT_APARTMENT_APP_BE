package com.apartmentbuilding.PTIT.Service.Apartment;

import com.apartmentbuilding.PTIT.Domains.ApartmentEntity;
import com.apartmentbuilding.PTIT.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.ExceptionAdvice.ExceptionVariable;
import com.apartmentbuilding.PTIT.Repository.IApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements IApartmentService {
    private final IApartmentRepository apartmentRepository;

    @Override
    @Transactional(readOnly = true)
    public ApartmentEntity findById(String id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.APARTMENT_NOT_FOUND));
    }
}
