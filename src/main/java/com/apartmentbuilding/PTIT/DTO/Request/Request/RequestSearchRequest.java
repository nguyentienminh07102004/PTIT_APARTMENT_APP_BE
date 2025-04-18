package com.apartmentbuilding.PTIT.DTO.Request.Request;

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
public class RequestSearchRequest {
    private Integer page;
    private Integer limit;
}
