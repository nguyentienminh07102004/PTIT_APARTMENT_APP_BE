package com.apartmentbuilding.PTIT.DTO.Request.Apartment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentRequest {
    @NotBlank(message = "APARTMENT_NAME_NOT_NULL_OR_EMPTY")
    @NotNull(message = "APARTMENT_NAME_NOT_NULL_OR_EMPTY")
    private String name;
    private String unitNumber;
    private String description;
    private Double area;
    private String floorName;
}
