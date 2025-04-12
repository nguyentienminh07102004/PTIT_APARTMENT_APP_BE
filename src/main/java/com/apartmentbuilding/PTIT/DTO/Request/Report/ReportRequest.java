package com.apartmentbuilding.PTIT.DTO.Request.Report;

import com.apartmentbuilding.PTIT.Common.Enum.ReportType;
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
public class ReportRequest {
    private String content;
    private String title;
    private String apartmentId;
    private ReportType type;
}
