package com.apartmentbuilding.PTIT.Model.Document;

import com.apartmentbuilding.PTIT.Common.Enum.ReportStatus;
import com.apartmentbuilding.PTIT.Common.Enum.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "Reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDocument {
    @MongoId
    private String id;
    private String email;
    private String apartmentId;
    private String title;
    private Object content;
    private ReportType type;
    private ReportStatus status;
    private Date createdDate;
}
