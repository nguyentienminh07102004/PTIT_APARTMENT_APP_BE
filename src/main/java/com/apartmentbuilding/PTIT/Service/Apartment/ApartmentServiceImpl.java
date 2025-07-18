package com.apartmentbuilding.PTIT.Service.Apartment;

import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Request.Apartment.ApartmentRequest;
import com.apartmentbuilding.PTIT.DTO.Request.Apartment.ApartmentSearchRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ApartmentResponse;
import com.apartmentbuilding.PTIT.Mapper.Apartment.ApartmentConvertor;
import com.apartmentbuilding.PTIT.Mapper.Apartment.IApartmentMapper;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import com.apartmentbuilding.PTIT.Model.Entity.FloorEntity;
import com.apartmentbuilding.PTIT.Repository.IApartmentRepository;
import com.apartmentbuilding.PTIT.Service.Floor.IFloorService;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import com.apartmentbuilding.PTIT.Utils.ReadExcel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements IApartmentService {
    private final IApartmentRepository apartmentRepository;
    private final IFloorService floorService;
    private final IApartmentMapper apartmentMapper;
    private final ApartmentConvertor apartmentConvertor;
    private final ReadExcel readExcel;

    @Override
    @Transactional(readOnly = true)
    public ApartmentEntity findById(String id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.APARTMENT_NOT_FOUND));
    }

    @Override
    public ApartmentEntity findByName(String name) {
        return this.apartmentRepository.findByName(name)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.APARTMENT_NOT_FOUND));
    }

    @Override
    @Transactional
    public ApartmentResponse save(ApartmentRequest apartmentRequest) {
        ApartmentEntity apartment = this.apartmentMapper.requestToEntity(apartmentRequest);
        if (StringUtils.hasText(apartmentRequest.getFloorName())) {
            FloorEntity floor = this.floorService.findByName(apartmentRequest.getFloorName());
            apartment.setFloor(floor);
        }
        this.apartmentRepository.save(apartment);
        return this.apartmentConvertor.entityToResponse(apartment);
    }

    @Override
    @Transactional
    public List<ApartmentResponse> saveFromExcel(MultipartFile file) {
        List<ApartmentRequest> apartmentRequests = this.readExcel.readExcel(file, 0, ApartmentRequest.class);
        List<ApartmentResponse> apartmentResponses = new ArrayList<>();
        for (ApartmentRequest apartmentRequest : apartmentRequests) {
            apartmentResponses.add(this.save(apartmentRequest));
        }
        return apartmentResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<ApartmentResponse> findAll(ApartmentSearchRequest apartmentSearchRequest) {
        Pageable pageable = PaginationUtils.pagination(apartmentSearchRequest.getPage(), apartmentSearchRequest.getLimit());
        Page<ApartmentEntity> apartmentEntities = this.apartmentRepository.findAll(pageable);
        Page<ApartmentResponse> apartmentResponses = apartmentEntities.map(apartmentConvertor::entityToResponse);
        return new PagedModel<>(apartmentResponses);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApartmentResponse> findMyApartment() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ApartmentEntity> myApartments = this.apartmentRepository.findByUser_Email(email);
        return myApartments.stream().map(apartmentConvertor::entityToResponse).toList();
    }

    @Override
    public List<String> getAllApartmentNames() {
        return this.apartmentRepository.findAllNames();
    }

    @Override
    public List<String> getAllApartmentNameByUserEmail(String email) {
        return this.apartmentRepository.findByApartmentNameByUserEmail(email);
    }
}
