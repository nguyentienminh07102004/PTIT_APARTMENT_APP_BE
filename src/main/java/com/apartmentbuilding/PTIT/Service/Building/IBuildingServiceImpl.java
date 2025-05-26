package com.apartmentbuilding.PTIT.Service.Building;

import com.apartmentbuilding.PTIT.DTO.Request.Building.BuildingSearchRequest;
import com.apartmentbuilding.PTIT.DTO.Request.Building.BuildingUpdateRequest;
import com.apartmentbuilding.PTIT.DTO.Response.BuildingResponse;
import com.apartmentbuilding.PTIT.Mapper.Building.IBuildingMapper;
import com.apartmentbuilding.PTIT.Model.Entity.BuildingEntity;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.apartmentbuilding.PTIT.Repository.IBuildingRepository;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IBuildingServiceImpl implements IBuildingService {
    private final IBuildingRepository buildingRepository;
    private final IBuildingMapper buildingMapper;

    @Override
    @Transactional(readOnly = true)
    public BuildingEntity findById(String id) {
        return this.buildingRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.BUILDING_NOT_FOUND));
    }

    @Override
    @Transactional
    public BuildingEntity save(BuildingEntity building) {
        return this.buildingRepository.save(building);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return this.buildingRepository.existsByName(name);
    }

    @Override
    @Transactional
    public BuildingResponse updateBuilding(BuildingUpdateRequest buildingUpdateRequest) {
        this.findById(buildingUpdateRequest.getId());
        BuildingEntity building = this.buildingMapper.toEntity(buildingUpdateRequest);
        this.buildingRepository.save(building);
        return this.buildingMapper.entityToResponse(building);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmpty() {
        return this.buildingRepository.count() == 0;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<BuildingResponse> findAll(BuildingSearchRequest buildingSearchRequest) {
        Page<BuildingEntity> buildingEntityPage = this.buildingRepository.findAll(PaginationUtils.pagination(buildingSearchRequest.getPage(), buildingSearchRequest.getLimit()));
        Page<BuildingResponse> buildingResponses = buildingEntityPage.map(buildingMapper::entityToResponse);
        return new PagedModel<>(buildingResponses);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuildingEntity> findAll() {
        return this.buildingRepository.findAll();
    }
}
