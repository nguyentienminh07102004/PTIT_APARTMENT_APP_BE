package com.apartmentbuilding.PTIT.DTO.Response.Request;

import com.apartmentbuilding.PTIT.Common.Enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestResponse {
    private String id;
    private String title;
    private String content;
    private RequestStatus status;
    private String image;
    private String createdBy;
    private Date createdDate;
    private String apartmentName;
}
