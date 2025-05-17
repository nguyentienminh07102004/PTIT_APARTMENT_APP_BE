package com.apartmentbuilding.PTIT.Service.Amenity;

import com.apartmentbuilding.PTIT.DTO.Response.Amenity.AmenityResponse;
import com.apartmentbuilding.PTIT.Mapper.Amenity.IAmenityMapper;
import com.apartmentbuilding.PTIT.Repository.IAmenityRepository;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements IAmenityService {
    private final IAmenityRepository amenityRepository;
    private final IAmenityMapper amenityMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedModel<AmenityResponse> findAllAmenity(Integer page, Integer limit) {
        return new PagedModel<>(this.amenityRepository
                .findAll(PaginationUtils.pagination(page, limit))
                .map(this.amenityMapper::toResponse));
    }
}
