package com.apartmentbuilding.PTIT.DTO.Request.Request;

import com.apartmentbuilding.PTIT.Common.Enums.RequestType;
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
public class RequestRequest {
    private String content;
    private String title;
    private String apartmentName;
    private RequestType type;
}
