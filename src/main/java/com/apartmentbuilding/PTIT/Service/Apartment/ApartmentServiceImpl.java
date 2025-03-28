package com.apartmentbuilding.PTIT.Service.Apartment;

import com.apartmentbuilding.PTIT.DTO.Reponse.ApartmentResponse;
import com.apartmentbuilding.PTIT.DTO.Request.Apartment.ApartmentRequest;
import com.apartmentbuilding.PTIT.Domains.ApartmentEntity;
import com.apartmentbuilding.PTIT.Domains.BuildingEntity;
import com.apartmentbuilding.PTIT.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.ExceptionAdvice.ExceptionVariable;
import com.apartmentbuilding.PTIT.Mapper.Apartment.IApartmentMapper;
import com.apartmentbuilding.PTIT.Repository.IApartmentRepository;
import com.apartmentbuilding.PTIT.Service.Building.IBuildingService;
import com.apartmentbuilding.PTIT.Utils.ReadExcel;
import lombok.RequiredArgsConstructor;
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
    private final IBuildingService buildingService;
    private final IApartmentMapper apartmentMapper;
    private final ReadExcel<ApartmentRequest> readExcel;

    @Override
    @Transactional(readOnly = true)
    public ApartmentEntity findById(String id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.APARTMENT_NOT_FOUND));
    }

    @Override
    @Transactional
    public ApartmentResponse save(ApartmentRequest apartmentRequest) {
        ApartmentEntity apartment = apartmentMapper.requestToEntity(apartmentRequest);
        if (StringUtils.hasText(apartmentRequest.getBuildingId())) {
            BuildingEntity building = buildingService.findById(apartmentRequest.getBuildingId());
            apartment.setBuilding(building);
        }
        apartmentRepository.save(apartment);
        return apartmentMapper.entityToResponse(apartment);
    }

    @Override
    @Transactional
    public List<ApartmentResponse> saveFromExcel(MultipartFile file) {
        List<ApartmentRequest> apartmentRequests = readExcel.readExcel(file, 0, ApartmentRequest.class);
        List<ApartmentResponse> apartmentResponses = new ArrayList<>();
        for (ApartmentRequest apartmentRequest : apartmentRequests) {
            apartmentResponses.add(this.save(apartmentRequest));
        }
        return apartmentResponses;
    }
}
