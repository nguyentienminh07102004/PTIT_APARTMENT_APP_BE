package com.apartmentbuilding.PTIT.DTO.Response.Amenity;

import com.apartmentbuilding.PTIT.Common.Enum.AmenityStatus;
import com.apartmentbuilding.PTIT.Common.Enum.AmenityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AmenityResponse {
    private String id;
    private String name;
    private String description;
    private AmenityType type;
    private AmenityStatus status;
    private Time openTime;
    private Time closeTime;
    private Boolean requiresBooking;
    private int maxCapacity;
    private String location;
    private Double usageFee;
    private String imageUrl;
    private Date lastMaintenanceDate;
    private String notes;
    private Boolean isBookable;
}
